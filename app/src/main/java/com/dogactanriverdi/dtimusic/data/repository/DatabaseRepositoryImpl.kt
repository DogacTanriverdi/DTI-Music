package com.dogactanriverdi.dtimusic.data.repository

import com.dogactanriverdi.dtimusic.data.model.Music
import com.dogactanriverdi.dtimusic.data.source.local.MusicDao
import com.dogactanriverdi.dtimusic.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val musicDao: MusicDao
) : DatabaseRepository {

    override suspend fun insertAllMusic(musicList: List<Music>) {
        return musicDao.insertAllMusic(musicList)
    }

    override fun getAllMusic(): Flow<List<Music>> {
        return musicDao.getAllMusic()
    }

    override fun getMusicById(id: Long): Flow<Music> {
        return musicDao.getMusicById(id)
    }

    override fun searchMusic(query: String): Flow<List<Music>> {
        return musicDao.searchMusic(query)
    }
}