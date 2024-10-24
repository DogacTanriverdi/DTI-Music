package com.dogactanriverdi.dtimusic.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dogactanriverdi.dtimusic.presentation.navigation.BottomNavigationBar
import com.dogactanriverdi.dtimusic.presentation.navigation.NavigationGraph
import com.dogactanriverdi.dtimusic.presentation.navigation.Screen
import com.dogactanriverdi.dtimusic.presentation.theme.DTIMusicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DTIMusicTheme {

                val navController = rememberNavController()

                var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    selectedItemIndex = when (destination.route) {
                        Screen.Home.route -> 0
                        Screen.Search.route -> 1
                        Screen.Library.route -> 2
                        else -> 0
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
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
                ) { innerPadding ->
                    NavigationGraph(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }
}