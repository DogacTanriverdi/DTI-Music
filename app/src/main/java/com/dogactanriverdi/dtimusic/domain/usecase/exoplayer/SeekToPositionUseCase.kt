package com.dogactanriverdi.dtimusic.domain.usecase.exoplayer

import com.dogactanriverdi.dtimusic.domain.repository.ExoPlayerRepository
import javax.inject.Inject

class SeekToPositionUseCase @Inject constructor(
    private val exoPlayerRepository: ExoPlayerRepository
) {
    operator fun invoke(position: Long) {
        exoPlayerRepository.seekToPosition(position)
    }
}