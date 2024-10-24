package com.dogactanriverdi.dtimusic.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dogactanriverdi.dtimusic.data.model.Music

@Database(entities = [Music::class], version = 1, exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun getMusicDao(): MusicDao
}