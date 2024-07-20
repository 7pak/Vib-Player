package com.abdts.musicplayerpractice.ui.home.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PauseCircleFilled
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.abdts.musicplayerpractice.R
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.ui.theme.MusicPlayerPracticeTheme

@Composable
fun AudioDetailBottomSheet(
    progress: Float,
    onProgress: (Float) -> Unit,
    isAudioPlaying: Boolean,
    currentPlayingAudio: Audio,
    onStar: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "Now playing",
            style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
        )

        Text(
            text = currentPlayingAudio.displayName,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.basicMarquee()
        )
        PlayingAudio(modifier = Modifier.padding(vertical = 25.dp), progress,currentPlayingAudio)
        Slider(
            value = progress,
            onValueChange = onProgress,
            valueRange = 0f..100f,
            modifier = Modifier.padding(12.dp)
        )
        AdjustMediaController(isAudioPlaying, onStar, onNext, onPrevious)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(.8f)
        ) {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(25.dp)) {
                Icon(
                    imageVector = Icons.Filled.Block,
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            }

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(25.dp)) {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
}

@Composable
fun PlayingAudio(modifier: Modifier = Modifier, progress: Float, audio: Audio) {
    val context = LocalContext.current

    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "${timestampToDuration(((progress * audio.duration)/100f).toLong())} - ${timestampToDuration(audio.duration.toLong())}",
            style = MaterialTheme.typography.labelMedium
        )

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(
                    audio.albumArtUri
                ).transformations(CircleCropTransformation()).placeholder(R.drawable.ic_music)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_music),
            error = painterResource(id = R.drawable.ic_music),
        )
        Spacer(modifier = Modifier.height(15.dp))

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .padding(15.dp)

        )
    }
}

@Composable
fun AdjustMediaController(
    isAudioPlaying: Boolean,
    onStar: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = onPrevious, modifier = Modifier.size(25.dp)) {
            Icon(
                imageVector = Icons.Filled.SkipPrevious,
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
        }

        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(35.dp)) {
            Icon(
                imageVector = Icons.Filled.FastRewind,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }

        IconButton(onClick = onStar, modifier = Modifier.size(80.dp)) {
            Icon(
                imageVector = if (isAudioPlaying) Icons.Filled.PauseCircleFilled else Icons.Filled.PlayCircleFilled,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(80.dp)
            )
        }

        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(35.dp)) {
            Icon(
                imageVector = Icons.Filled.FastForward,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }

        IconButton(onClick = onNext, modifier = Modifier.size(25.dp)) {
            Icon(
                imageVector = Icons.Filled.SkipNext,
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AudioDetailBottomSheetPreview() {
    MusicPlayerPracticeTheme {
        //AudioDetailBottomSheet()
    }
}


