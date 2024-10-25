package com.dogactanriverdi.dtimusic.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dogactanriverdi.dtimusic.data.model.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAlbum(albumList: List<Album>)

    @Query("SELECT * FROM ALBUM_ENTITY ORDER BY dateAdded DESC")
    fun getAllAlbum(): Flow<List<Album>>

    @Query("SELECT * FROM ALBUM_ENTITY WHERE id = :id")
    fun getAlbumById(id: Long): Flow<Album>

    @Query("SELECT * FROM ALBUM_ENTITY WHERE name LIKE '%' || :query || '%'")
    fun searchAlbum(query: String): Flow<List<Album>>
}