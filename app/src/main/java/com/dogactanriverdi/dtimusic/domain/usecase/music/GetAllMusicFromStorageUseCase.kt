package com.dogactanriverdi.dtimusic.domain.usecase.music

import com.dogactanriverdi.dtimusic.data.model.Music
import com.dogactanriverdi.dtimusic.domain.repository.MusicRepository
import javax.inject.Inject

class GetAllMusicFromStorageUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(): List<Music> {
        return musicRepository.getAllMusicFromStorage()
    }
}