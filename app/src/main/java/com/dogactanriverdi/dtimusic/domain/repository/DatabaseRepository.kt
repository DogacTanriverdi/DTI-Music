package com.dogactanriverdi.dtimusic.domain.repository

import com.dogactanriverdi.dtimusic.data.model.Music
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun insertAllMusic(musicList: List<Music>)

    fun getAllMusic(): Flow<List<Music>>

    fun getMusicById(id: Long): Flow<Music>

    fun searchMusic(query: String): Flow<List<Music>>
}