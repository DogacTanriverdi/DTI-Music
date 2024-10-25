package com.dogactanriverdi.dtimusic.data.repository

import com.dogactanriverdi.dtimusic.data.model.Album
import com.dogactanriverdi.dtimusic.data.model.Music
import com.dogactanriverdi.dtimusic.data.source.local.AlbumDao
import com.dogactanriverdi.dtimusic.data.source.local.MusicDao
import com.dogactanriverdi.dtimusic.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val musicDao: MusicDao,
    private val albumDao: AlbumDao
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

    override suspend fun insertAllAlbum(albumList: List<Album>) {
        return albumDao.insertAllAlbum(albumList)
    }

    override fun getAllAlbum(): Flow<List<Album>> {
        return albumDao.getAllAlbum()
    }

    override fun getAlbumById(id: Long): Flow<Album> {
        return albumDao.getAlbumById(id)
    }

    override fun searchAlbum(query: String): Flow<List<Album>> {
        return albumDao.searchAlbum(query)
    }
}