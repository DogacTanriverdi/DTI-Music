package com.dogactanriverdi.dtimusic.presentation.navigation

sealed class Screen(val route: String) {
    data object Home: Screen(route = "Home")
    data object Search: Screen(route = "Search")
    data object Library: Screen(route = "Library")
}