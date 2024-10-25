package com.dogactanriverdi.dtimusic.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dogactanriverdi.dtimusic.common.Constants.ALBUM_ENTITY

@Entity(tableName = ALBUM_ENTITY)
data class Album(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val artist: String,
    val albumArtUri: String,
    val dateAdded: Long,
    val musicList: List<Music>
)
