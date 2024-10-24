package com.dogactanriverdi.dtimusic.domain.usecase.database

import com.dogactanriverdi.dtimusic.data.model.Music
import com.dogactanriverdi.dtimusic.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMusicByIdUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    operator fun invoke(id: Long): Flow<Music> {
        return databaseRepository.getMusicById(id)
    }
}