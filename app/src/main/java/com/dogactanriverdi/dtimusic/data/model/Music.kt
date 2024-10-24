package com.dogactanriverdi.dtimusic.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dogactanriverdi.dtimusic.common.Constants.MUSIC_ENTITY

@Entity(tableName = MUSIC_ENTITY)
data class Music(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val title: String,
    val artist: String,
    val duration: Long,
    val album: String,
    val dateAdded: Long,
    val albumArtUri: String,
    val contentUri: String
) {

    companion object {
        val default = Music(
            id = -1,
            title = "",
            artist = "<unknown>",
            duration = -1,
            album = "",
            dateAdded = -1,
            albumArtUri = "",
            contentUri = ""
        )
    }
}