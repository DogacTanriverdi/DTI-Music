package com.dogactanriverdi.dtimusic.data.di

import com.dogactanriverdi.dtimusic.data.repository.DatabaseRepositoryImpl
import com.dogactanriverdi.dtimusic.data.repository.ExoPlayerRepositoryImpl
import com.dogactanriverdi.dtimusic.data.repository.MusicRepositoryImpl
import com.dogactanriverdi.dtimusic.domain.repository.DatabaseRepository
import com.dogactanriverdi.dtimusic.domain.repository.ExoPlayerRepository
import com.dogactanriverdi.dtimusic.domain.repository.MusicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMusicRepository(repositoryImpl: MusicRepositoryImpl): MusicRepository

    @Binds
    abstract fun bindExoPlayerRepository(repositoryImpl: ExoPlayerRepositoryImpl): ExoPlayerRepository

    @Binds
    abstract fun bindDatabaseRepository(repositoryImpl: DatabaseRepositoryImpl): DatabaseRepository
}