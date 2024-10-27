package com.dogactanriverdi.dtimusic.presentation.search

import com.dogactanriverdi.dtimusic.data.model.Album
import com.dogactanriverdi.dtimusic.data.model.Music

object SearchContract {

    data class UiState(
        val musicResult: List<Music> = emptyList(),
        val albumResult: List<Album> = emptyList()
    )

    sealed interface UiAction {
        data class SearchMusic(val query: String) : UiAction
        data class SearchAlbum(val query: String) : UiAction
    }

    sealed class UiEffect
}