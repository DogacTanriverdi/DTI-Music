package com.dogactanriverdi.dtimusic.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.dogactanriverdi.dtimusic.R
import com.dogactanriverdi.dtimusic.data.model.Music
import com.dogactanriverdi.dtimusic.presentation.home.components.CheckAndRequestPermission
import com.dogactanriverdi.dtimusic.presentation.main.MainViewModel
import com.dogactanriverdi.dtimusic.presentation.home.HomeContract.UiState
import com.dogactanriverdi.dtimusic.presentation.home.HomeContract.UiEffect
import com.dogactanriverdi.dtimusic.presentation.home.HomeContract.UiAction
import com.dogactanriverdi.dtimusic.presentation.home.components.MusicItem
import com.dogactanriverdi.dtimusic.presentation.main.MainContract
import com.dogactanriverdi.dtimusic.presentation.navigation.Screen
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
            musicList = uiState.musicList,
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
    musicList: List<Music>,
    viewModel: HomeViewModel,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    mainViewModel: MainViewModel,
    mainOnAction: (MainContract.UiAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = stringResource(R.string.recently_added),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .weight(1f)
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