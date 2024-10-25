package com.dogactanriverdi.dtimusic.domain.usecase.database

import com.dogactanriverdi.dtimusic.data.model.Album
import com.dogactanriverdi.dtimusic.domain.repository.DatabaseRepository
import javax.inject.Inject

class InsertAllAlbumUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(albums: List<Album>) {
        databaseRepository.insertAllAlbum(albums)
    }
}