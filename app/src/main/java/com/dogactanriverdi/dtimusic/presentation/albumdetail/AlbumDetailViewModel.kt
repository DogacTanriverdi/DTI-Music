package com.dogactanriverdi.dtimusic.presentation.albumdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.dtimusic.domain.usecase.database.DatabaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.dogactanriverdi.dtimusic.presentation.albumdetail.AlbumDetailContract.UiState
import com.dogactanriverdi.dtimusic.presentation.albumdetail.AlbumDetailContract.UiEffect
import com.dogactanriverdi.dtimusic.presentation.albumdetail.AlbumDetailContract.UiAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val databaseUseCases: DatabaseUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {}

    fun getAlbumById(albumId: Long) {
        viewModelScope.launch {
            databaseUseCases.getAlbumById(albumId).collect { album ->
                updateUiState { copy(album = album) }
            }
        }
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.value = _uiState.value.block()
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}