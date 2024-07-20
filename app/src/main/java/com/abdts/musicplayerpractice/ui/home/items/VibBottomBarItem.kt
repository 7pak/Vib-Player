package com.abdts.musicplayerpractice.ui.home.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircleOutline
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.abdts.musicplayerpractice.R
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.ui.theme.MusicPlayerPracticeTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VibBottomBarItem(
    audio: Audio,
    progress: Float,
    onProgress: (Float) -> Unit,
    isAudioPlaying: Boolean,
    currentPlaying:Audio,
    onStar: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    val context = LocalContext.current

    var isBottomSheetExpanded by rememberSaveable {
        mutableStateOf(false)
    }



    val sheetState = rememberModalBottomSheetState()
    if (isBottomSheetExpanded){
        ModalBottomSheet(
            onDismissRequest = {isBottomSheetExpanded =false},
            sheetState = sheetState,

            content = {
                AudioDetailBottomSheet(
                    progress = progress,
                    onProgress = onProgress,
                    isAudioPlaying = isAudioPlaying,
                    currentPlayingAudio = currentPlaying,
                    onStar = onStar,
                    onNext = onNext, onPrevious = onPrevious
                )
            },
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .background(Color.Black)
            .padding(top = 5.dp)
            .clickable {
                isBottomSheetExpanded = true
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Row(Modifier.fillMaxWidth(.9f), verticalAlignment = Alignment.CenterVertically) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(
                        audio.albumArtUri.toString().ifEmpty { R.drawable.ic_music }
                    ).transformations(CircleCropTransformation()).placeholder(R.drawable.ic_music).build(),
                placeholder = painterResource(id = R.drawable.ic_music),
                error = painterResource(id = R.drawable.ic_music)
            )

            Image(painter = painter, contentDescription = null, modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = audio.displayName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.basicMarquee()
                )
                Text(
                    text = audio.artist,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.basicMarquee()

                )
            }
            IconButton(onClick = onStar) {
                Icon(
                    imageVector = if (isAudioPlaying) Icons.Filled.PauseCircleOutline else Icons.Filled.PlayCircleOutline,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun VibBottomBar() {
    MusicPlayerPracticeTheme {
        VibBottomBarItem(
            audio = Audio("".toUri(), 0, "Title1", "little", "", 200454, "this ", "".toUri()),
            progress = 25f,
            currentPlaying = Audio("".toUri(), 0, "Title1", "little", "", 200454, "this ", "".toUri()),
            onProgress = {},
            isAudioPlaying = true,
            onStar = { /*TODO*/ }, onNext = {}) {

        }
    }
}