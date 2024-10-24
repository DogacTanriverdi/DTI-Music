package com.dogactanriverdi.dtimusic.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dogactanriverdi.dtimusic.presentation.home.HomeScreen
import com.dogactanriverdi.dtimusic.presentation.library.LibraryScreen
import com.dogactanriverdi.dtimusic.presentation.search.SearchScreen

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = Modifier.then(modifier),
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(route = Screen.Home.route) {
            HomeScreen()
        }

        composable(route = Screen.Search.route) {
            SearchScreen()
        }

        composable(route = Screen.Library.route) {
            LibraryScreen()
        }
    }
}