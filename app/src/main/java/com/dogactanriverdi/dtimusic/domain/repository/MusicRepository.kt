package com.dogactanriverdi.dtimusic.domain.repository

import com.dogactanriverdi.dtimusic.data.model.Album
import com.dogactanriverdi.dtimusic.data.model.Music

interface MusicRepository {

    fun getAllMusicFromStorage(): List<Music>

    fun getMusicByIdFromStorage(musicId: Long): Music

    fun getAllAlbumFromStorage(): List<Album>
}