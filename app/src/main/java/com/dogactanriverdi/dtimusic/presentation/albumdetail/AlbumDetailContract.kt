package com.dogactanriverdi.dtimusic.presentation.albumdetail

import com.dogactanriverdi.dtimusic.data.model.Album

object AlbumDetailContract {

    data class UiState(
        val album: Album = Album.default
    )

    sealed interface UiAction

    sealed class UiEffect
}