package com.dogactanriverdi.dtimusic.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.dogactanriverdi.dtimusic.R
import com.dogactanriverdi.dtimusic.presentation.home.HomeContract.UiAction
import com.dogactanriverdi.dtimusic.presentation.home.HomeContract.UiEffect
import com.dogactanriverdi.dtimusic.presentation.home.HomeContract.UiState
import com.dogactanriverdi.dtimusic.presentation.home.components.CheckAndRequestPermission
import com.dogactanriverdi.dtimusic.presentation.home.components.MusicItem
import com.dogactanriverdi.dtimusic.presentation.main.MainContract
import com.dogactanriverdi.dtimusic.presentation.main.MainViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    mainViewModel: MainViewModel,
    mainOnAction: (MainContract.UiAction) -> Unit,
) {
    CheckAndRequestPermission {
        val mainMusicList = mainViewModel.getAllMusic()
        viewModel.insertAllMusic(mainMusicList)
        viewModel.getAllMusic()

        HomeContent(
            viewModel = viewModel,
            uiState = uiState,
            onAction = onAction,
            mainViewModel = mainViewModel,
            mainOnAction = mainOnAction
        )
    }
}

@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    mainViewModel: MainViewModel,
    mainOnAction: (MainContract.UiAction) -> Unit,
) {
    with(uiState) {

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Text(
                modifier = Modifier.padding(20.dp),
                text = stringResource(R.string.recently_added),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {
                items(musicList) { music ->
                    MusicItem(
                        title = music.title,
                        artist = music.artist,
                        albumArtUri = music.albumArtUri.toUri(),
                        duration = music.duration
                    ) {
                        mainViewModel.updateCurrentMusic(music)
                        mainOnAction(MainContract.UiAction.ShowBottomPlayer)
                        mainOnAction(MainContract.UiAction.Play(music))
                        mainViewModel.updateIsPlaying(true)
                        mainViewModel.updateMusicFrom("Recently added")
                    }
                }
            }
        }
    }
}