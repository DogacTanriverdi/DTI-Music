package com.dogactanriverdi.dtimusic.domain.repository

import android.net.Uri

interface ExoPlayerRepository {

    fun play(uri: Uri)

    fun pause()

    fun skipToNext()

    fun skipToPrevious()

    fun seekToPosition(position: Long)

    fun getCurrentPosition(): Long

    fun exoPlayerIsPlaying(uri: Uri): Boolean

    fun resume()
}