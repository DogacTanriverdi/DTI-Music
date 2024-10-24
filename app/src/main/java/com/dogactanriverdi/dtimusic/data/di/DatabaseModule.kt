package com.dogactanriverdi.dtimusic.data.di

import android.content.Context
import androidx.room.Room
import com.dogactanriverdi.dtimusic.common.Constants.MUSIC_DATABASE
import com.dogactanriverdi.dtimusic.data.source.local.MusicDao
import com.dogactanriverdi.dtimusic.data.source.local.MusicDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @[Provides Singleton]
    fun provideMusicDatabase(
        @ApplicationContext context: Context
    ): MusicDatabase {
        return Room.databaseBuilder(
            context,
            MusicDatabase::class.java,
            MUSIC_DATABASE
        ).build()
    }

    @[Provides Singleton]
    fun provideMusicDao(
        musicDatabase: MusicDatabase
    ): MusicDao {
        return musicDatabase.getMusicDao()
    }
}