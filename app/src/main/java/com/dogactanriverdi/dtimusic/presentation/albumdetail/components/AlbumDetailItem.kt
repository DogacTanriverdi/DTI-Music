package com.dogactanriverdi.dtimusic.presentation.albumdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun AlbumDetailItem(
    title: String,
    artist: String,
    duration: Long,
    onItemClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {

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