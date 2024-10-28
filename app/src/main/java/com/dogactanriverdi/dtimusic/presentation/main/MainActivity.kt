package com.dogactanriverdi.dtimusic.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.dogactanriverdi.dtimusic.presentation.main.MainContract.UiAction
import com.dogactanriverdi.dtimusic.presentation.main.components.BottomPlayer
import com.dogactanriverdi.dtimusic.presentation.main.components.ExpandedPlayer
import com.dogactanriverdi.dtimusic.presentation.navigation.BottomNavigationBar
import com.dogactanriverdi.dtimusic.presentation.navigation.NavigationGraph
import com.dogactanriverdi.dtimusic.presentation.navigation.Screen
import com.dogactanriverdi.dtimusic.presentation.theme.DTIMusicTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DTIMusicTheme {

                val viewModel: MainViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val onAction = viewModel::onAction

                val navController = rememberNavController()

                var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

                var bottomPlayerPadding by remember { mutableStateOf(0.dp) }

                val sheetState = rememberBottomSheetScaffoldState()
                val scope = rememberCoroutineScope()

                val musicList = viewModel.getAllMusic()

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    selectedItemIndex = when (destination.route) {
                        Screen.Home.route -> 0
                        Screen.Search.route -> 1
                        Screen.Library.route -> 2
                        else -> 0
                    }
                }

                with(uiState) {

                    LaunchedEffect(Unit) {
                        while (true) {
                            viewModel.getCurrentPosition()
                            delay(100L)
                        }
                    }

                    LaunchedEffect(isBottomSheetVisible) {
                        bottomPlayerPadding = if (isBottomSheetVisible) 100.dp else 0.dp
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            if (sheetState.bottomSheetState.currentValue != SheetValue.Expanded) {
                                BottomNavigationBar(
                                    navController = navController,
                                    selectedItemIndex = selectedItemIndex,
                                    onItemSelected = { index, item ->
                                        selectedItemIndex = index
                                        navController.navigate(item.title) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {

                            NavigationGraph(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = bottomPlayerPadding),
                                navController = navController,
                                mainViewModel = viewModel
                            )

                            BackHandler(
                                enabled = sheetState.bottomSheetState.currentValue == SheetValue.Expanded
                            ) {
                                scope.launch {
                                    sheetState.bottomSheetState.partialExpand()
                                }
                            }

                            BottomSheetScaffold(
                                scaffoldState = sheetState,
                                sheetContent = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(
                                                indication = null,
                                                interactionSource = remember { MutableInteractionSource() }
                                            )
                                            {
                                                if (sheetState.bottomSheetState.currentValue == SheetValue.PartiallyExpanded) {
                                                    scope.launch {
                                                        sheetState.bottomSheetState.expand()
                                                    }
                                                }
                                            }
                                    ) {
                                        if (sheetState.bottomSheetState.currentValue == SheetValue.PartiallyExpanded) {
                                            BottomPlayer(
                                                music = currentMusic,
                                                isPlaying = isPlaying,
                                                onSkipPreviousClicked = {
                                                    val currentIndex =
                                                        musicList.indexOfFirst { it.id == currentMusic.id }
                                                    if (currentIndex > 0) {
                                                        val previousMusic =
                                                            musicList[currentIndex - 1]
                                                        viewModel.updateCurrentMusic(previousMusic)
                                                        onAction(
                                                            UiAction.SkipToPrevious(
                                                                previousMusic.contentUri.toUri()
                                                            )
                                                        )
                                                        viewModel.updateIsPlaying(isPlaying = true)
                                                    }
                                                },
                                                onPlayPauseClicked = {
                                                    if (isPlaying) {
                                                        onAction(
                                                            UiAction.PlayPause(isPlaying = true)
                                                        )
                                                        viewModel.updateIsPlaying(isPlaying = false)
                                                    } else {
                                                        onAction(UiAction.PlayPause(isPlaying = false))
                                                        viewModel.updateIsPlaying(isPlaying = true)
                                                    }
                                                },
                                                onSkipNextClicked = {
                                                    val currentIndex =
                                                        musicList.indexOfFirst { it.id == currentMusic.id }
                                                    if (currentIndex != -1 && currentIndex < musicList.size - 1) {
                                                        val nextMusic = musicList[currentIndex + 1]
                                                        viewModel.updateCurrentMusic(nextMusic)
                                                        onAction(UiAction.SkipToNext(nextMusic.contentUri.toUri()))
                                                        viewModel.updateIsPlaying(isPlaying = true)
                                                    }
                                                },
                                            )
                                        } else {
                                            ExpandedPlayer(
                                                music = currentMusic,
                                                isPlaying = isPlaying,
                                                onSkipPreviousClicked = {
                                                    val currentIndex =
                                                        musicList.indexOfFirst { it.id == currentMusic.id }
                                                    if (currentIndex > 0) {
                                                        val previousMusic =
                                                            musicList[currentIndex - 1]
                                                        viewModel.updateCurrentMusic(previousMusic)
                                                        onAction(
                                                            UiAction.SkipToPrevious(
                                                                previousMusic.contentUri.toUri()
                                                            )
                                                        )
                                                        viewModel.updateIsPlaying(isPlaying = true)
                                                    }
                                                },
                                                currentPosition = uiState.currentPosition,
                                                musicFrom = uiState.musicFrom,
                                                onPlayPauseClicked = {
                                                    if (isPlaying) {
                                                        onAction(
                                                            UiAction.PlayPause(isPlaying = true)
                                                        )
                                                        viewModel.updateIsPlaying(isPlaying = false)
                                                    } else {
                                                        onAction(UiAction.PlayPause(false))
                                                        viewModel.updateIsPlaying(isPlaying = true)
                                                    }
                                                },
                                                onSkipNextClicked = {
                                                    val currentIndex =
                                                        musicList.indexOfFirst { it.id == currentMusic.id }
                                                    if (currentIndex != -1 && currentIndex < musicList.size - 1) {
                                                        val nextMusic = musicList[currentIndex + 1]
                                                        viewModel.updateCurrentMusic(nextMusic)
                                                        onAction(UiAction.SkipToNext(nextMusic.contentUri.toUri()))
                                                        viewModel.updateIsPlaying(isPlaying = true)
                                                    }
                                                },
                                                onPositionChanged = { position ->
                                                    viewModel.seekToPosition(position)
                                                },
                                                onCollapseClicked = {
                                                    scope.launch {
                                                        sheetState.bottomSheetState.partialExpand()
                                                    }
                                                }
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier.align(Alignment.BottomCenter),
                                sheetPeekHeight = if (isBottomSheetVisible) 100.dp else 0.dp,
                                sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                                containerColor = MaterialTheme.colorScheme.surface,
                                sheetDragHandle = {
                                    Box(
                                        modifier = Modifier.height(1.dp)
                                    )
                                }
                            ) {}
                        }
                    }
                }
            }
        }
    }
}