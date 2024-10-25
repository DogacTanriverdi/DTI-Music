package com.dogactanriverdi.dtimusic.presentation.albumdetail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dogactanriverdi.dtimusic.R
import com.dogactanriverdi.dtimusic.presentation.main.MainContract
import com.dogactanriverdi.dtimusic.presentation.main.MainViewModel
import com.dogactanriverdi.dtimusic.presentation.albumdetail.AlbumDetailContract.UiState
import com.dogactanriverdi.dtimusic.presentation.albumdetail.AlbumDetailContract.UiEffect
import com.dogactanriverdi.dtimusic.presentation.albumdetail.AlbumDetailContract.UiAction
import com.dogactanriverdi.dtimusic.presentation.albumdetail.components.AlbumDetailItem
import kotlinx.coroutines.flow.Flow

@Composable
fun AlbumDetailScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    viewModel: AlbumDetailViewModel,
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    mainViewModel: MainViewModel,
    mainOnAction: (MainContract.UiAction) -> Unit,
) {
    backStackEntry.arguments?.getLong("albumId")?.let { albumId ->
        viewModel.getAlbumById(albumId)

        AlbumDetailContent(
            navController = navController,
            viewModel = viewModel,
            uiState = uiState,
            onAction = onAction,
            mainViewModel = mainViewModel,
            mainOnAction = mainOnAction
        )
    } ?: return
}

@Composable
fun AlbumDetailContent(
    navController: NavController,
    viewModel: AlbumDetailViewModel,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    mainViewModel: MainViewModel,
    mainOnAction: (MainContract.UiAction) -> Unit,
) {
    with(uiState) {

        val context = LocalContext.current

        val isValidUri = remember(album.albumArtUri) {
            try {
                context.contentResolver.openInputStream(album.albumArtUri.toUri())?.close()
                true
            } catch (e: Exception) {
                false
            }
        }

        val isDarkTheme = isSystemInDarkTheme()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            IconButton(
                modifier = Modifier.padding(10.dp),
                onClick = { navController.navigateUp() },
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            LazyColumn(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    if (isValidUri) {
                        AsyncImage(
                            model = album.albumArtUri.toUri(),
                            contentDescription = "Album Art",
                            modifier = Modifier
                                .size(300.dp)
                                .padding(10.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .align(Alignment.CenterHorizontally),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        AsyncImage(
                            model = R.drawable.ic_music_note,
                            contentDescription = "Album Art",
                            modifier = Modifier
                                .size(300.dp)
                                .padding(10.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .align(Alignment.CenterHorizontally),
                            contentScale = ContentScale.Crop,
                            colorFilter = if (isDarkTheme) ColorFilter.tint(Color.White) else null
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        text = album.name,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        text = album.artist,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }

                items(album.musicList) { music ->
                    AlbumDetailItem(
                        title = music.title,
                        artist = music.artist,
                        duration = music.duration
                    ) {
                        mainViewModel.updateCurrentMusic(music)
                        mainOnAction(MainContract.UiAction.ShowBottomPlayer)
                        mainOnAction(MainContract.UiAction.Play(music))
                        mainViewModel.updateIsPlaying(true)
                        mainViewModel.updateMusicFrom("Album")
                    }
                }
            }
        }
    }
}