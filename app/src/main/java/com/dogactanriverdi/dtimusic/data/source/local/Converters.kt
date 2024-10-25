package com.dogactanriverdi.dtimusic.data.source.local

import androidx.room.TypeConverter
import com.dogactanriverdi.dtimusic.data.model.Music

class Converters {

    @TypeConverter
    fun fromMusicList(musicList: List<Music>): String {
        return musicList.joinToString(separator = ";") { music ->
            "${music.id},${music.title},${music.artist},${music.duration},${music.album},${music.albumId},${music.dateAdded},${music.albumArtUri},${music.contentUri}"
        }
    }

    // String -> List<Music>
    @TypeConverter
    fun toMusicList(data: String): List<Music> {
        return if (data.isEmpty()) {
            emptyList()
        } else {
            data.split(";").map { item ->
                val parts = item.split(",")
                Music(
                    id = parts[0].toLong(),
                    title = parts[1],
                    artist = parts[2],
                    duration = parts[3].toLong(),
                    album = parts[4],
                    albumId = parts[5].toLong(),
                    dateAdded = parts[6].toLong(),
                    albumArtUri = parts[7],
                    contentUri = parts[8],
                )
            }
        }
    }
}