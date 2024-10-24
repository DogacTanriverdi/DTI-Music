package com.dogactanriverdi.dtimusic.data.di

import com.dogactanriverdi.dtimusic.domain.usecase.database.DatabaseUseCases
import com.dogactanriverdi.dtimusic.domain.usecase.database.GetAllMusicUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.database.GetMusicByIdUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.database.InsertAllMusicUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.database.SearchMusicUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.music.GetAllMusicFromStorageUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.music.GetMusicByIdFromStorageUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.music.MusicUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @[Provides Singleton]
    fun provideDatabaseUseCases(
        insertAllMusicUseCase: InsertAllMusicUseCase,
        getAllMusicUseCase: GetAllMusicUseCase,
        getMusicByIdUseCase: GetMusicByIdUseCase,
        searchMusicUseCase: SearchMusicUseCase
    ): DatabaseUseCases {
        return DatabaseUseCases(
            insertAllMusic = insertAllMusicUseCase,
            getAllMusic = getAllMusicUseCase,
            getMusicById = getMusicByIdUseCase,
            searchMusic = searchMusicUseCase
        )
    }

    @[Provides Singleton]
    fun provideMusicUseCases(
        getAllMusicFromStorageUseCase: GetAllMusicFromStorageUseCase,
        getMusicByIdFromStorageUseCase: GetMusicByIdFromStorageUseCase
    ): MusicUseCases {
        return MusicUseCases(
            getAllMusicFromStorage = getAllMusicFromStorageUseCase,
            getMusicByIdFromStorage = getMusicByIdFromStorageUseCase
        )
    }
}