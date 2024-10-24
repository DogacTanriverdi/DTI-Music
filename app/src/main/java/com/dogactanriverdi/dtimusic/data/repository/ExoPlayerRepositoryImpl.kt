package com.dogactanriverdi.dtimusic.data.repository

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.dogactanriverdi.dtimusic.domain.repository.ExoPlayerRepository
import javax.inject.Inject

class ExoPlayerRepositoryImpl @Inject constructor(
    private val exoPlayer: ExoPlayer
) : ExoPlayerRepository {

    override fun play(uri: Uri) {
        exoPlayer.setMediaItem(MediaItem.fromUri(uri))
        exoPlayer.prepare()
        exoPlayer.play()
    }

    override fun pause() {
        exoPlayer.pause()
    }

    override fun skipToNext() {
        exoPlayer.seekToNext()
    }

    override fun skipToPrevious() {
        exoPlayer.seekToPrevious()
    }

    override fun seekToPosition(position: Long) {
        exoPlayer.seekTo(position)
    }

    override fun getCurrentPosition(): Long {
        return exoPlayer.currentPosition
    }

    override fun exoPlayerIsPlaying(uri: Uri): Boolean {
        val currentMediaItem = exoPlayer.currentMediaItem
        return currentMediaItem?.mediaId == uri.toString() && exoPlayer.isPlaying
    }

    override fun resume() {
        if (exoPlayer.playWhenReady.not() && exoPlayer.currentMediaItem != null) {
            exoPlayer.play()
        }
    }
}