package com.abdts.musicplayerpractice.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdts.musicplayerpractice.ui.SharedViewModel
import com.abdts.musicplayerpractice.ui.home.HomeEvent
import com.abdts.musicplayerpractice.ui.home.HomeScreen
import com.abdts.musicplayerpractice.ui.home.HomeViewModel
import com.abdts.musicplayerpractice.ui.home.items.HomeBottomBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    sharedViewModel:SharedViewModel
) {
    val audioControllerUiState = sharedViewModel.audioControllerUiState
    NavHost(navController =navHostController , startDestination = Screens.HomeScreen){
        composable<Screens.HomeScreen> {
            val mainViewModel: HomeViewModel = koinViewModel()
            val isInitialized = rememberSaveable { mutableStateOf(false) }

            if (!isInitialized.value) {
                LaunchedEffect(key1 = Unit) {
                    mainViewModel.onEvent(HomeEvent.FetchSong)
                    isInitialized.value = true
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreen(
                    onEvent = mainViewModel::onEvent,
                    uiState = mainViewModel.homeUiState,
                )
                HomeBottomBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onEvent = mainViewModel::onEvent,
                    song = audioControllerUiState.currentSong,
                    playerState = audioControllerUiState.playerState,
                    onBarClick = {  }
                )
            }
        }
    }
}