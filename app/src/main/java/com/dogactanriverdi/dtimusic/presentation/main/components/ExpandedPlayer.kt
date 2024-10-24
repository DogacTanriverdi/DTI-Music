package com.dogactanriverdi.dtimusic.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.PlaylistAdd
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.dogactanriverdi.dtimusic.R
import com.dogactanriverdi.dtimusic.data.model.Music
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedPlayer(
    modifier: Modifier = Modifier,
    music: Music,
    isPlaying: Boolean,
    currentPosition: Long,
    musicFrom: String,
    onSkipPreviousClicked: () -> Unit,
    onPlayPauseClicked: () -> Unit,
    onSkipNextClicked: () -> Unit,
    onPositionChanged: (Long) -> Unit,
    onCollapseClicked: () -> Unit,
    onAddToPlaylistClicked: () -> Unit
) {
    with(music) {

        val context = LocalContext.current

        val modalBottomSheetState = rememberModalBottomSheetState()

        var isBottomSheetVisible by remember { mutableStateOf(false) }

        val isValidUri = remember(albumArtUri) {
            try {
                context.contentResolver.openInputStream(albumArtUri.toUri())?.close()
                true
            } catch (e: Exception) {
                false
            }
        }

        val isDarkTheme = isSystemInDarkTheme()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IconButton(
                    onClick = { onCollapseClicked() },
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Play",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "From",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = musicFrom,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                }

                IconButton(
                    onClick = { isBottomSheetVisible = true },
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            if (isBottomSheetVisible) {
                ModalBottomSheet(
                    onDismissRequest = {
                        isBottomSheetVisible = false
                    },
                    sheetState = modalBottomSheetState,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onAddToPlaylistClicked() }
                    ) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.PlaylistAdd,
                            contentDescription = "PlaylistAdd",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(30.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "Add to playlist",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Şarkının resmi Coil ile yükleniyor
            if (isValidUri) {
                AsyncImage(
                    model = albumArtUri,  // Coil kullanarak albüm resmi yükleniyor
                    contentDescription = "Album Art",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 30.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                AsyncImage(
                    model = R.drawable.ic_music_note,  // Coil kullanarak albüm resmi yükleniyor
                    contentDescription = "Album Art",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 30.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(8.dp)),
                    colorFilter = if (isDarkTheme) ColorFilter.tint(Color.White) else null
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .weight(0.7f)
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 32.dp)
                )

                Text(
                    text = artist,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 32.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Slider(
                    value = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f,
                    onValueChange = { newValue ->
                        val newPosition = (newValue * duration).toLong()
                        onPositionChanged(newPosition)
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.onBackground,
                        activeTrackColor = MaterialTheme.colorScheme.onBackground,
                        inactiveTrackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = formatDuration(currentPosition),
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(start = 20.dp)
                    )

                    Text(
                        text = formatDuration(duration),
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(end = 20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    IconButton(
                        onClick = { onSkipPreviousClicked() },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Skip previous",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(70.dp)
                        )
                    }

                    IconButton(
                        onClick = { onPlayPauseClicked() },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 10.dp)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(70.dp)
                        )
                    }

                    IconButton(
                        onClick = { onSkipNextClicked() },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Skip next",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(70.dp)
                        )
                    }
                }
            }
        }
    }
}

fun formatDuration(duration: Long): String {
    val minutes = duration / 1000 / 60
    val seconds = (duration / 1000 % 60).toInt()
    return String.format(Locale.getDefault(), "%d:%02d", minutes, seconds)
}