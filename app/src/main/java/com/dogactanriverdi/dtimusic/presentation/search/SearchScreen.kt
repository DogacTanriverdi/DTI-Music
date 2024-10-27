package com.dogactanriverdi.dtimusic.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.dogactanriverdi.dtimusic.presentation.main.MainContract
import com.dogactanriverdi.dtimusic.presentation.main.MainViewModel
import com.dogactanriverdi.dtimusic.presentation.navigation.Screen
import com.dogactanriverdi.dtimusic.presentation.search.SearchContract.UiAction
import com.dogactanriverdi.dtimusic.presentation.search.SearchContract.UiState
import com.dogactanriverdi.dtimusic.presentation.search.components.MusicItem

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel,
    uiState: UiState,
    mainViewModel: MainViewModel,
    mainOnAction: (MainContract.UiAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SearchContent(
            navController = navController,
            viewModel = viewModel,
            uiState = uiState,
            viewModel::onAction,
            mainViewModel = mainViewModel,
            mainOnAction = mainOnAction,
        )
    }
}

@Composable
fun SearchContent(
    navController: NavController,
    viewModel: SearchViewModel,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    mainViewModel: MainViewModel,
    mainOnAction: (MainContract.UiAction) -> Unit,
) {

    var searchQuery by rememberSaveable { mutableStateOf("") }

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    val tabTitles = listOf("Musics", "Albums")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        with(uiState) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = searchQuery,
                onValueChange = {
                    searchQuery = it

                    when (selectedTabIndex) {

                        0 -> {
                            onAction(UiAction.SearchMusic(searchQuery))
                        }

                        1 -> {
                            onAction(UiAction.SearchAlbum(searchQuery))
                        }
                    }
                },
                label = { Text("Search") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                tabTitles.forEachIndexed { index, title ->
                    FilterChip(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        label = { Text(text = title) },
                        shape = CircleShape,
                    )
                }
            }

            LazyVerticalGrid(
                modifier = Modifier.padding(10.dp),
                columns = GridCells.Adaptive(100.dp)
            ) {
                when (selectedTabIndex) {

                    0 -> {
                        items(uiState.musicResult) { music ->
                            MusicItem(
                                name = music.title,
                                artist = music.artist,
                                albumArtUri = music.albumArtUri.toUri()
                            ) {
                                mainViewModel.updateCurrentMusic(music)
                                mainOnAction(MainContract.UiAction.ShowBottomPlayer)
                                mainOnAction(MainContract.UiAction.Play(music))
                                mainViewModel.updateIsPlaying(true)
                                mainViewModel.updateMusicFrom("Search: $searchQuery")
                            }
                        }
                    }

                    1 -> {
                        items(uiState.albumResult) { album ->
                            MusicItem(
                                name = album.name,
                                artist = album.artist,
                                albumArtUri = album.albumArtUri.toUri()
                            ) {
                                navController.navigate(
                                    route = Screen.AlbumDetail.route + "?albumId=${album.id}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}