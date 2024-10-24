package com.dogactanriverdi.dtimusic.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.dtimusic.data.model.Music
import com.dogactanriverdi.dtimusic.domain.usecase.database.DatabaseUseCases
import com.dogactanriverdi.dtimusic.presentation.home.HomeContract.UiState
import com.dogactanriverdi.dtimusic.presentation.home.HomeContract.UiEffect
import com.dogactanriverdi.dtimusic.presentation.home.HomeContract.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val databaseUseCases: DatabaseUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {}

    fun insertAllMusic(musicList: List<Music>) {
        viewModelScope.launch {
            databaseUseCases.insertAllMusic(musicList)
        }
    }

    fun getAllMusic() {
        viewModelScope.launch {
            databaseUseCases.getAllMusic().collect { musicList ->
                updateUiState { copy(musicList = musicList) }
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