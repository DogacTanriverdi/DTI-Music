package com.dogactanriverdi.dtimusic.domain.usecase.database

import com.dogactanriverdi.dtimusic.data.model.Music
import com.dogactanriverdi.dtimusic.domain.repository.DatabaseRepository
import javax.inject.Inject

class InsertAllMusicUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(musicList: List<Music>) {
        databaseRepository.insertAllMusic(musicList)
    }
}