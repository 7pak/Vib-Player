package com.abdts.musicplayerpractice.ui.home.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.abdts.musicplayerpractice.R
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.ui.theme.MusicPlayerPracticeTheme
import kotlin.math.floor

@Composable
fun AudioItem(audio: Audio, isPlaying: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }, verticalAlignment = Alignment.CenterVertically
    ) {
        if (isPlaying){
            Icon(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.width(6.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = audio.displayName,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = audio.artist,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        Text(text = timestampToDuration(audio.duration.toLong()), style = MaterialTheme.typography.labelMedium)

        Spacer(modifier = Modifier.size(8.dp))
    }
}

fun timestampToDuration(position: Long): String {
    val totalSeconds = floor(position / 1E3).toInt()
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds - (minutes * 60)
    return if (position < 0) "--:--" else "%d:%02d".format(minutes, remainingSeconds)
}

@Preview(showBackground = true)
@Composable
private fun AudioItemPreview() {
    MusicPlayerPracticeTheme {
        AudioItem(
            audio = Audio("".toUri(), 0, "Title1", "little", "", 200454, "this ", "".toUri()),
            isPlaying = true
        ) {

        }
    }
}