package com.dogactanriverdi.dtimusic.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dogactanriverdi.dtimusic.presentation.home.HomeScreen
import com.dogactanriverdi.dtimusic.presentation.home.HomeViewModel
import com.dogactanriverdi.dtimusic.presentation.library.LibraryScreen
import com.dogactanriverdi.dtimusic.presentation.library.LibraryViewModel
import com.dogactanriverdi.dtimusic.presentation.main.MainViewModel
import com.dogactanriverdi.dtimusic.presentation.search.SearchScreen

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    NavHost(
        modifier = Modifier.then(modifier),
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(route = Screen.Home.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect

            HomeScreen(
                viewModel = viewModel,
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                mainViewModel = mainViewModel,
                mainOnAction = mainViewModel::onAction
            )
        }

        composable(route = Screen.Search.route) {
            SearchScreen()
        }

        composable(route = Screen.Library.route) {
            val viewModel: LibraryViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            val onAction = viewModel::onAction

            LibraryScreen(
                viewModel = viewModel,
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = onAction,
                mainViewModel = mainViewModel,
                mainOnAction = mainViewModel::onAction
            )
        }
    }
}