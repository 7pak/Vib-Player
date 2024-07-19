package com.abdts.musicplayerpractice.ui.home.items

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PauseCircleOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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


@Composable
fun VibBottomBarItem(
    audio: Audio,
    progress: Float,
    onProgress: (Float) -> Unit,
    isAudioPlaying: Boolean,
    onStar: () -> Unit,
    onNext: () -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .background(Color.Black)
            .padding(top = 5.dp)
            ,
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
private fun VibBottomBar() {
    MusicPlayerPracticeTheme {
        VibBottomBarItem(
            audio = Audio("".toUri(), 0, "Title1", "little", "", 200454, "this ", "".toUri()),
            progress = 25f,
            onProgress = {},
            isAudioPlaying = true,
            onStar = { /*TODO*/ }) {

        }
    }
}