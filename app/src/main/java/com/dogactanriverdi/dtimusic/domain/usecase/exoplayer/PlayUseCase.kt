package com.dogactanriverdi.dtimusic.domain.usecase.exoplayer

import android.net.Uri
import com.dogactanriverdi.dtimusic.domain.repository.ExoPlayerRepository
import javax.inject.Inject

class PlayUseCase @Inject constructor(
    private val exoPlayerRepository: ExoPlayerRepository
) {
    operator fun invoke(uri: Uri) {
        exoPlayerRepository.play(uri)
    }
}