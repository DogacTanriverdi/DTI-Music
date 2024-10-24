package com.dogactanriverdi.dtimusic.presentation.main

import android.net.Uri
import com.dogactanriverdi.dtimusic.data.model.Music

object MainContract {

    data class UiState(
        val currentPosition: Long = 0L,
        val isBottomSheetVisible: Boolean = false,
        val isPlaying: Boolean = false,
        val currentMusic: Music = Music.default,
        val musicFrom: String = ""
    )

    sealed interface UiAction {
        data class Play(val music: Music) : UiAction
        data class PlayPause(val isPlaying: Boolean) : UiAction
        data class SkipToPrevious(val uri: Uri) : UiAction
        data class SkipToNext(val uri: Uri) : UiAction
        data object ShowBottomPlayer : UiAction
        data object HideBottomPlayer : UiAction
    }

    sealed class UiEffect
}