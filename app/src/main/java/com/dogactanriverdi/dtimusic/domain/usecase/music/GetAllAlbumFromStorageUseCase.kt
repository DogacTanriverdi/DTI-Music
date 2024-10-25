package com.dogactanriverdi.dtimusic.domain.usecase.music

import com.dogactanriverdi.dtimusic.data.model.Album
import com.dogactanriverdi.dtimusic.domain.repository.MusicRepository
import javax.inject.Inject

class GetAllAlbumFromStorageUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(): List<Album> {
        return musicRepository.getAllAlbumFromStorage()
    }
}