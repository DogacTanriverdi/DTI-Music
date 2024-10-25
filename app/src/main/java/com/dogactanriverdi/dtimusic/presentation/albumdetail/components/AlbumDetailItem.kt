package com.dogactanriverdi.dtimusic.presentation.albumdetail.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dogactanriverdi.dtimusic.R
import java.util.Locale

@Composable
fun AlbumDetailItem(
    title: String,
    artist: String,
    albumArtUri: Uri,
    duration: Long,
    onItemClick: () -> Unit
) {

    val context = LocalContext.current

    val isValidUri = remember(albumArtUri) {
        try {
            context.contentResolver.openInputStream(albumArtUri)?.close()
            true
        } catch (e: Exception) {
            false
        }
    }

    val isDarkTheme = isSystemInDarkTheme()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (isValidUri) {
            AsyncImage(
                model = albumArtUri,
                contentDescription = "Album Art",
                modifier = Modifier
                    .size(70.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = R.drawable.ic_music_note,
                contentDescription = "Album Art",
                modifier = Modifier
                    .size(90.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                colorFilter = if (isDarkTheme) ColorFilter.tint(Color.White) else null
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {

            Text(
                text = title,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(end = 10.dp)
            )

            Text(
                text = artist,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(end = 10.dp)
            )
        }

        Text(
            text = formatDuration(duration),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

private fun formatDuration(duration: Long): String {
    val minutes = duration / 1000 / 60
    val seconds = (duration / 1000 % 60).toInt()
    return String.format(Locale.getDefault(), "%d:%02d", minutes, seconds)
}