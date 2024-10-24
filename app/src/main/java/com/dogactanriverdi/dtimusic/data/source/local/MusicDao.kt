package com.dogactanriverdi.dtimusic.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dogactanriverdi.dtimusic.data.model.Music
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMusic(musicList: List<Music>)

    @Query("SELECT * FROM MUSIC_ENTITY ORDER BY dateAdded DESC")
    fun getAllMusic(): Flow<List<Music>>

    @Query("SELECT * FROM MUSIC_ENTITY WHERE id = :id")
    fun getMusicById(id: Long): Flow<Music>

    @Query("SELECT * FROM MUSIC_ENTITY WHERE title LIKE '%' || :query || '%'")
    fun searchMusic(query: String): Flow<List<Music>>
}