package com.dogactanriverdi.dtimusic.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dogactanriverdi.dtimusic.data.model.Album
import com.dogactanriverdi.dtimusic.data.model.Music

@Database(entities = [Music::class, Album::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun getMusicDao(): MusicDao
    abstract fun getAlbumDao(): AlbumDao
}