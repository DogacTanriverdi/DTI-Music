package com.dogactanriverdi.dtimusic.presentation.main

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.dogactanriverdi.dtimusic.data.model.Album
import com.dogactanriverdi.dtimusic.data.model.Music
import com.dogactanriverdi.dtimusic.domain.usecase.exoplayer.ExoPlayerUseCases
import com.dogactanriverdi.dtimusic.domain.usecase.music.MusicUseCases
import com.dogactanriverdi.dtimusic.presentation.main.MainContract.UiState
import com.dogactanriverdi.dtimusic.presentation.main.MainContract.UiEffect
import com.dogactanriverdi.dtimusic.presentation.main.MainContract.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val musicUseCases: MusicUseCases,
    private val exoPlayerUseCases: ExoPlayerUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {

            is UiAction.Play -> {
                play(uiAction.music.contentUri.toUri())
            }

            is UiAction.PlayPause -> {
                if (uiAction.isPlaying)
                    pause()
                else
                    resume()
            }

            is UiAction.SkipToPrevious -> {
                skipToPrevious(uiAction.uri)
            }

            is UiAction.SkipToNext -> {
                skipToNext(uiAction.uri)
            }

            is UiAction.ShowBottomPlayer -> {
                updateUiState { copy(isBottomSheetVisible = true) }
            }

            is UiAction.HideBottomPlayer -> {
                updateUiState { copy(isBottomSheetVisible = false) }
            }
        }
    }

    fun getAllMusic(): List<Music> {
        return musicUseCases.getAllMusicFromStorage()
    }

    fun getMusicById(musicId: Long): Music {
        val music = musicUseCases.getMusicByIdFromStorage(musicId)
        return music
    }

    fun getAllAlbum(): List<Album> {
        return musicUseCases.getAllAlbumFromStorage()
    }

    private fun play(uri: Uri) {
        exoPlayerUseCases.play(uri)
    }

    private fun pause() {
        exoPlayerUseCases.pause()
    }

    private fun resume() {
        exoPlayerUseCases.resume()
    }

    fun seekToPosition(position: Long) {
        exoPlayerUseCases.seekToPosition(position)
    }

    private fun skipToNext(uri: Uri) {
        exoPlayerUseCases.skipToNext()
        play(uri)
    }

    private fun skipToPrevious(uri: Uri) {
        exoPlayerUseCases.skipToPrevious()
        play(uri)
    }

    fun getCurrentPosition() {
        updateUiState { copy(currentPosition = exoPlayerUseCases.getCurrentPosition()) }
    }

    fun updateCurrentMusic(music: Music) {
        updateUiState { copy(currentMusic = music) }
    }

    fun updateIsPlaying(isPlaying: Boolean) {
        updateUiState { copy(isPlaying = isPlaying) }
    }

    fun updateMusicFrom(musicFrom: String) {
        updateUiState { copy(musicFrom = musicFrom) }
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}