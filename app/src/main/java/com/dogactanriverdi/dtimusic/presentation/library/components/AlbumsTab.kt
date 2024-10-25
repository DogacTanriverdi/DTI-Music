package com.dogactanriverdi.dtimusic.presentation.library.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.dogactanriverdi.dtimusic.R
import com.dogactanriverdi.dtimusic.data.model.Album

@Composable
fun AlbumsTab(
    albumList: List<Album>,
    onItemClicked: (Long) -> Unit
) {
    Column {

        Text(
            modifier = Modifier.padding(20.dp),
            text = stringResource(R.string.albums),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        LazyVerticalGrid(
            modifier = Modifier.padding(10.dp),
            columns = GridCells.Adaptive(100.dp)
        ) {
            items(albumList) { album ->
                AlbumItem(
                    name = album.name,
                    artist = album.artist,
                    albumArtUri = album.albumArtUri.toUri()
                ) { onItemClicked(album.id) }
            }
        }
    }
}