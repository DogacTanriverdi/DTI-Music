package com.dogactanriverdi.dtimusic.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.dtimusic.domain.usecase.database.DatabaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dogactanriverdi.dtimusic.presentation.search.SearchContract.UiState
import com.dogactanriverdi.dtimusic.presentation.search.SearchContract.UiEffect
import com.dogactanriverdi.dtimusic.presentation.search.SearchContract.UiAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val databaseUseCases: DatabaseUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {

            is UiAction.SearchMusic -> {
                searchMusic(uiAction.query)
            }

            is UiAction.SearchAlbum -> {
                searchAlbum(uiAction.query)
            }
        }
    }

    private fun searchMusic(query: String) {
        viewModelScope.launch {
            databaseUseCases.searchMusic(query).collect { musicList ->
                _uiState.value = _uiState.value.copy(musicResult = musicList)
            }
        }
    }

    private fun searchAlbum(query: String) {
        viewModelScope.launch {
            databaseUseCases.searchAlbum(query).collect { albumList ->
                _uiState.value = _uiState.value.copy(albumResult = albumList)
            }
        }
    }
}