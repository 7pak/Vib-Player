package com.abdts.musicplayerpractice.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.ui.home.items.AudioItem
import com.abdts.musicplayerpractice.ui.home.items.VibBottomBarItem
import com.abdts.musicplayerpractice.ui.home.items.VibTopAppBar
import com.abdts.musicplayerpractice.ui.theme.MusicPlayerPracticeTheme

@Composable
fun HomeScreen(
    progress: Float,
    onProgress: (Float) -> Unit,
    isAudioPlaying: Boolean,
    audioList: List<Audio>,
    currentPlayingAudio: Audio,
    onStar: () -> Unit,
    onItemClicked: (Int) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {

    var currentPlaying by remember {
        mutableStateOf(currentPlayingAudio)
    }

    LaunchedEffect (currentPlayingAudio){
        if (currentPlayingAudio.uri.toString().isNotEmpty()){
            currentPlaying = currentPlayingAudio
        }
    }



    Scaffold(
        topBar = { VibTopAppBar() },
        bottomBar = {
            if (currentPlaying.id >0){
                VibBottomBarItem(
                    audio = currentPlaying,
                    progress = progress,
                    onProgress = onProgress,
                    currentPlaying = currentPlaying,
                    isAudioPlaying = isAudioPlaying,
                    onStar = onStar, onNext = onNext, onPrevious = onPrevious
                )
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding, modifier = Modifier
            .fillMaxSize()) {
            itemsIndexed(audioList) { index, audio ->
                AudioItem(audio = audio, onClick = {
                    onItemClicked(index)
                    currentPlaying = audio
                }, isPlaying = audio.id == currentPlaying.id)
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPrev() {
    MusicPlayerPracticeTheme {
        HomeScreen(
            progress = 80f,
            onProgress = {},
            isAudioPlaying = true,
            audioList = listOf(
                Audio("".toUri(), 0, "Title1", "little", "", 200454, "this ", "".toUri()),
                Audio("".toUri(), 0, "Title2", "litte two", "", 451584, "this", "".toUri())
            ),
            currentPlayingAudio = Audio("".toUri(), 0, "First", "second", "", 0, "", "".toUri()),
            onStar = { /*TODO*/ },
            onItemClicked = {}, onNext = {}
        ) {

        }
    }
}