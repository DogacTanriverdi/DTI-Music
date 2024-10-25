package com.dogactanriverdi.dtimusic.presentation.library

import com.dogactanriverdi.dtimusic.data.model.Album

object LibraryContract {

    data class UiState(
        val albumList: List<Album> = emptyList()
    )

    sealed interface UiAction

    sealed class UiEffect
}