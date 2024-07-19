package com.abdts.musicplayerpractice.ui.home

import androidx.compose.foundation.BorderStroke
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

    Scaffold(
        bottomBar = {
            BottomBarPlayer(
                audio = currentPlayingAudio,
                progress = progress,
                onProgress = onProgress,
                isAudioPlaying = isAudioPlaying,
                onStar = onStar, onNext = onNext
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            itemsIndexed(audioList) { index, audio ->
                AudioItem(audio = audio, onClick = { onItemClicked(index) })
            }
        }
    }
}

@Composable
fun AudioItem(audio: Audio, onClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() }, verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp), verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = audio.displayName,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = audio.artist,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        Text(text = timestampToDuration(audio.duration.toLong()))

        Spacer(modifier = Modifier.size(8.dp))
    }
}

private fun timestampToDuration(position: Long): String {
    val totalSeconds = floor(position / 1E3).toInt()
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds - (minutes * 60)
    return if (position < 0) "--:--" else "%d:%02d".format(minutes, remainingSeconds)
}


@Composable
fun BottomBarPlayer(
    audio: Audio,
    progress: Float,
    onProgress: (Float) -> Unit,
    isAudioPlaying: Boolean,
    onStar: () -> Unit,
    onNext: () -> Unit
) {

    BottomAppBar {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ArtistInfo(audio = audio, modifier = Modifier.weight(1f))
                MediaPlayerController(
                    isAudioPlaying = isAudioPlaying,
                    onStar = onStar,
                    onNext = onNext
                )

                Slider(value = progress, onValueChange = onProgress, valueRange = 0f..100f)
            }
        }
    }
}

@Composable
fun MediaPlayerController(
    isAudioPlaying: Boolean,
    onStar: () -> Unit,
    onNext: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .height(55.dp)
            .padding(4.dp)
    ) {
        PlayerIcon(icon = if (isAudioPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow) {
            onStar()
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Filled.SkipNext,
            contentDescription = null,
            modifier = Modifier.clickable {
                onNext()
            })
    }
}


@Composable
fun ArtistInfo(
    modifier: Modifier = Modifier,
    audio: Audio
) {

    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayerIcon(
            icon = Icons.Default.Info,
            borderStroke = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onSurface)
        ) {}
        Spacer(modifier = Modifier.width(4.dp))

        Column {
            Text(
                text = audio.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = audio.artist,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
fun PlayerIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    borderStroke: BorderStroke? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    color: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        border = borderStroke,
        modifier = modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }, contentColor = color, color = backgroundColor
    ) {

        Box(modifier = Modifier.padding(4.dp), contentAlignment = Alignment.Center) {
            Icon(imageVector = icon, contentDescription = null)
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
            isAudioPlaying = false,
            audioList = listOf(
                Audio("".toUri(), 0, "Title1", "little", "", 200454, "this ", "".toUri()),
                Audio("".toUri(), 0, "Title2", "litte two", "", 451584, "this", "".toUri())
            ),
            currentPlayingAudio = Audio("".toUri(), 0, "", "", "", 0, "", "".toUri()),
            onStar = { /*TODO*/ },
            onItemClicked = {}
        ) {

        }
    }
}