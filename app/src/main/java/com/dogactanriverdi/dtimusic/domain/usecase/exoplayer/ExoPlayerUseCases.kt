package com.dogactanriverdi.dtimusic.domain.usecase.exoplayer

data class ExoPlayerUseCases(
    val play: PlayUseCase,
    val pause: PauseUseCase,
    val skipToNext: SkipToNextUseCase,
    val skipToPrevious: SkipToPreviousUseCase,
    val seekToPosition: SeekToPositionUseCase,
    val getCurrentPosition: GetCurrentPositionUseCase,
    val exoPlayerIsPlaying: ExoPlayerIsPlayingUseCase,
    val resume: ResumeUseCase
)