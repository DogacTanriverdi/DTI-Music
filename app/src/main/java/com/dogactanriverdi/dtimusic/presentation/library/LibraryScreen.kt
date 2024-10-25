package com.dogactanriverdi.dtimusic.presentation.library

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.dogactanriverdi.dtimusic.R
import com.dogactanriverdi.dtimusic.presentation.library.LibraryContract.UiState
import com.dogactanriverdi.dtimusic.presentation.library.LibraryContract.UiEffect
import com.dogactanriverdi.dtimusic.presentation.library.LibraryContract.UiAction
import com.dogactanriverdi.dtimusic.presentation.library.components.AlbumItem
import com.dogactanriverdi.dtimusic.presentation.main.MainContract
import com.dogactanriverdi.dtimusic.presentation.main.MainViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun LibraryScreen(
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
        viewModel = viewModel,
        uiState = uiState,
        onAction = onAction,
        mainViewModel = mainViewModel,
        mainOnAction = mainOnAction
    )
}

@Composable
fun LibraryContent(
    viewModel: LibraryViewModel,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    mainViewModel: MainViewModel,
    mainOnAction: (MainContract.UiAction) -> Unit
) {
    with(uiState) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = stringResource(R.string.albums),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            LazyRow(
                modifier = Modifier.padding(10.dp)
            ) {
                items(albumList) { album ->
                    AlbumItem(
                        name = album.name,
                        artist = album.artist,
                        albumArtUri = album.albumArtUri.toUri()
                    ) {

                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}