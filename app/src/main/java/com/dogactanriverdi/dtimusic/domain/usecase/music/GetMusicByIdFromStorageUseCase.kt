package com.dogactanriverdi.dtimusic.domain.usecase.music

import com.dogactanriverdi.dtimusic.data.model.Music
import com.dogactanriverdi.dtimusic.domain.repository.MusicRepository
import javax.inject.Inject

class GetMusicByIdFromStorageUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(id: Long): Music {
        return musicRepository.getMusicByIdFromStorage(id)
    }
}