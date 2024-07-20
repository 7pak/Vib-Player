package com.abdts.musicplayerpractice.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdts.musicplayerpractice.ui.home.HomeScreen
import com.abdts.musicplayerpractice.ui.home.HomeViewModel
import com.abdts.musicplayerpractice.ui.home.UIEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    navHostController: NavHostController,onStarService:()->Unit) {
    NavHost(navController =navHostController , startDestination = Screens.HomeScreen){
        composable<Screens.HomeScreen> {
            val viewModel:HomeViewModel = koinViewModel()
            HomeScreen(
                progress = viewModel.progress,
                onProgress = { viewModel.onUIEvent(UIEvents.SeekTo(it)) },
                isAudioPlaying = viewModel.isPlaying,
                audioList = viewModel.audioList,
                currentPlayingAudio = viewModel.currentSelectedAudio,
                onStar = { viewModel.onUIEvent(UIEvents.PlayPause) },
                onItemClicked = {
                    viewModel.onUIEvent(UIEvents.SelectedAudioChange(it))
                    onStarService()
                },
                onNext = {viewModel.onUIEvent(UIEvents.SeekToNext)},
                onPrevious = {viewModel.onUIEvent(UIEvents.SeekToPrevious)}
            )
        }
    }
}