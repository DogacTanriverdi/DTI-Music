package com.dogactanriverdi.dtimusic.presentation.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.dtimusic.data.model.Album
import com.dogactanriverdi.dtimusic.domain.usecase.database.DatabaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.dogactanriverdi.dtimusic.presentation.library.LibraryContract.UiState
import com.dogactanriverdi.dtimusic.presentation.library.LibraryContract.UiEffect
import com.dogactanriverdi.dtimusic.presentation.library.LibraryContract.UiAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val databaseUseCases: DatabaseUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {}

    fun insertAllAlbum(albumList: List<Album>) {
        viewModelScope.launch {
            databaseUseCases.insertAllAlbum(albumList)
        }
    }

    fun getAllAlbum() {
        viewModelScope.launch {
            databaseUseCases.getAllAlbum().collect { albumList ->
                updateUiState { copy(albumList = albumList) }
            }
        }
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}