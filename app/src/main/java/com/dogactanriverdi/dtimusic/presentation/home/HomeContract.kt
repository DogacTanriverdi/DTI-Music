package com.dogactanriverdi.dtimusic.presentation.home

import com.dogactanriverdi.dtimusic.data.model.Music

object HomeContract {

    data class UiState(
        val musicList: List<Music> = emptyList()
    )

    sealed interface UiAction {

    }

    sealed class UiEffect
}