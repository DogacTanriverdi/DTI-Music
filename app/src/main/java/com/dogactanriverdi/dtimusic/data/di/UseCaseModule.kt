package com.dogactanriverdi.dtimusic.data.di

import com.dogactanriverdi.dtimusic.domain.usecase.database.DatabaseUseCases
import com.dogactanriverdi.dtimusic.domain.usecase.database.GetAllMusicUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.database.GetMusicByIdUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.database.InsertAllMusicUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.database.SearchMusicUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.exoplayer.ExoPlayerIsPlayingUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.exoplayer.ExoPlayerUseCases
import com.dogactanriverdi.dtimusic.domain.usecase.exoplayer.GetCurrentPositionUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.exoplayer.PauseUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.exoplayer.PlayUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.exoplayer.ResumeUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.exoplayer.SeekToPositionUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.exoplayer.SkipToNextUseCase
import com.dogactanriverdi.dtimusic.domain.usecase.exoplayer.SkipToPreviousUseCase
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

    @[Provides Singleton]
    fun provideExoPlayerUseCases(
        playUseCase: PlayUseCase,
        pauseUseCase: PauseUseCase,
        skipToNextUseCase: SkipToNextUseCase,
        skipToPreviousUseCase: SkipToPreviousUseCase,
        seekToPositionUseCase: SeekToPositionUseCase,
        getCurrentPositionUseCase: GetCurrentPositionUseCase,
        exoPlayerIsPlayingUseCase: ExoPlayerIsPlayingUseCase,
        resumeUseCase: ResumeUseCase
    ): ExoPlayerUseCases {
        return ExoPlayerUseCases(
            play = playUseCase,
            pause = pauseUseCase,
            skipToNext = skipToNextUseCase,
            skipToPrevious = skipToPreviousUseCase,
            seekToPosition = seekToPositionUseCase,
            getCurrentPosition = getCurrentPositionUseCase,
            exoPlayerIsPlaying = exoPlayerIsPlayingUseCase,
            resume = resumeUseCase
        )
    }
}