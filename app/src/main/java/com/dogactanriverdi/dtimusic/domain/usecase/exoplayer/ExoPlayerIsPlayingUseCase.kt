package com.dogactanriverdi.dtimusic.domain.usecase.exoplayer

import android.net.Uri
import com.dogactanriverdi.dtimusic.domain.repository.ExoPlayerRepository
import javax.inject.Inject

class ExoPlayerIsPlayingUseCase @Inject constructor(
    private val exoPlayerRepository: ExoPlayerRepository
) {
    operator fun invoke(uri: Uri): Boolean {
        return exoPlayerRepository.exoPlayerIsPlaying(uri)
    }
}