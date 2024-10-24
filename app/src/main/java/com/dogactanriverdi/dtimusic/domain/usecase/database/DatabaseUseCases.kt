package com.dogactanriverdi.dtimusic.domain.usecase.database

data class DatabaseUseCases(
    val insertAllMusic: InsertAllMusicUseCase,
    val getAllMusic: GetAllMusicUseCase,
    val getMusicById: GetMusicByIdUseCase,
    val searchMusic: SearchMusicUseCase,
)
