package com.dogactanriverdi.dtimusic.domain.usecase.database

import com.dogactanriverdi.dtimusic.data.model.Album
import com.dogactanriverdi.dtimusic.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchAlbumUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    operator fun invoke(query: String): Flow<List<Album>> {
        return databaseRepository.searchAlbum(query)
    }
}