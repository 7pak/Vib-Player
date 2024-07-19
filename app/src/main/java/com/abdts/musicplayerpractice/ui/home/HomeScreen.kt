package com.abdts.musicplayerpractice.ui.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.ui.home.items.AudioItem
import com.abdts.musicplayerpractice.ui.home.items.BottomBarPlayer
import com.abdts.musicplayerpractice.ui.home.items.VibBottomBarItem
import com.abdts.musicplayerpractice.ui.home.items.VibTopAppBar
import com.abdts.musicplayerpractice.ui.theme.MusicPlayerPracticeTheme
import kotlin.math.floor

@Composable
fun HomeScreen(
    progress: Float,
    onProgress: (Float) -> Unit,
    isAudioPlaying: Boolean,
    audioList: List<Audio>,
    currentPlayingAudio: Audio,
    onStar: () -> Unit,
    onItemClicked: (Int) -> Unit,
    onNext: () -> Unit
) {

    var currentPlaying by remember {
        mutableStateOf(currentPlayingAudio)
    }

    Scaffold(
        topBar = { VibTopAppBar() },
        bottomBar = {
            if (currentPlaying.id >0){
                VibBottomBarItem(
                    audio = currentPlaying,
                    progress = progress,
                    onProgress = onProgress,
                    isAudioPlaying = isAudioPlaying,
                    onStar = onStar, onNext = onNext
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
            onItemClicked = {}
        ) {

        }
    }
}