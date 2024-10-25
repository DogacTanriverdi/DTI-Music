package com.dogactanriverdi.dtimusic.domain.repository

import com.dogactanriverdi.dtimusic.data.model.Album
import com.dogactanriverdi.dtimusic.data.model.Music
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun insertAllMusic(musicList: List<Music>)

    fun getAllMusic(): Flow<List<Music>>

    fun getMusicById(id: Long): Flow<Music>

    fun searchMusic(query: String): Flow<List<Music>>

    suspend fun insertAllAlbum(albumList: List<Album>)

    fun getAllAlbum(): Flow<List<Album>>

    fun getAlbumById(id: Long): Flow<Album>

    fun searchAlbum(query: String): Flow<List<Album>>
}