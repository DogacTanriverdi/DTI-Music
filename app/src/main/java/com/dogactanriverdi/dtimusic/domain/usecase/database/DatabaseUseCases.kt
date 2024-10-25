package com.dogactanriverdi.dtimusic.domain.usecase.database

data class DatabaseUseCases(
    val insertAllMusic: InsertAllMusicUseCase,
    val getAllMusic: GetAllMusicUseCase,
    val getMusicById: GetMusicByIdUseCase,
    val searchMusic: SearchMusicUseCase,
    val insertAllAlbum: InsertAllAlbumUseCase,
    val getAllAlbum: GetAllAlbumUseCase,
    val getAlbumById: GetAlbumByIdUseCase,
    val searchAlbum: SearchAlbumUseCase,
)
