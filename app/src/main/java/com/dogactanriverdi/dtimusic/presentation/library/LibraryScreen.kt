package com.dogactanriverdi.dtimusic.presentation.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dogactanriverdi.dtimusic.presentation.library.LibraryContract.UiState
import com.dogactanriverdi.dtimusic.presentation.library.LibraryContract.UiEffect
import com.dogactanriverdi.dtimusic.presentation.library.LibraryContract.UiAction
import com.dogactanriverdi.dtimusic.presentation.library.components.AlbumsTab
import com.dogactanriverdi.dtimusic.presentation.main.MainContract
import com.dogactanriverdi.dtimusic.presentation.main.MainViewModel
import com.dogactanriverdi.dtimusic.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow

@Composable
fun LibraryScreen(
    navController: NavController,
    viewModel: LibraryViewModel,
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    mainViewModel: MainViewModel,
    mainOnAction: (MainContract.UiAction) -> Unit
) {
    val mainAlbumList = mainViewModel.getAllAlbum()
    viewModel.insertAllAlbum(mainAlbumList)
    viewModel.getAllAlbum()

    LibraryContent(
        navController = navController,
        viewModel = viewModel,
        uiState = uiState,
        onAction = onAction,
        mainViewModel = mainViewModel,
        mainOnAction = mainOnAction
    )
}

@Composable
fun LibraryContent(
    navController: NavController,
    viewModel: LibraryViewModel,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    mainViewModel: MainViewModel,
    mainOnAction: (MainContract.UiAction) -> Unit
) {
    with(uiState) {

        var selectedTabIndex by remember { mutableIntStateOf(0) }

        val tabTitles = listOf("Albums", "Playlists")

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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

            when (selectedTabIndex) {

                0 -> {
                    AlbumsTab(albumList) { albumId ->
                        navController.navigate(
                            route = Screen.AlbumDetail.route + "?albumId=$albumId"
                        )
                    }
                }

                1 -> {
                    Text(text = "Playlists")
                }
            }
        }
    }
}