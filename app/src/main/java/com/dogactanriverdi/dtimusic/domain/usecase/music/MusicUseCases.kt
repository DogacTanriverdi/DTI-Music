package com.dogactanriverdi.dtimusic.domain.usecase.music

data class MusicUseCases(
    val getAllMusicFromStorage: GetAllMusicFromStorageUseCase,
    val getMusicByIdFromStorage: GetMusicByIdFromStorageUseCase,
    val getAllAlbumFromStorage: GetAllAlbumFromStorageUseCase,
)
