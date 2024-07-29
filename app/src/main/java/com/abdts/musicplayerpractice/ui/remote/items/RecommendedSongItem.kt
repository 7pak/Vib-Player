package com.abdts.musicplayerpractice.ui.remote.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdts.musicplayerpractice.R
import com.abdts.musicplayerpractice.ui.remote.RecommendedSong
import com.abdts.musicplayerpractice.ui.theme.LightBlack
import com.abdts.musicplayerpractice.ui.theme.MusicPlayerPracticeTheme

@Composable
fun RecommendedSongItem(
    recommendedSong: RecommendedSong
) {

    Row (modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(25.dp))
        .clickable {  }
        .background(LightBlack), verticalAlignment = Alignment.CenterVertically){

        Image(
            painter = painterResource(id = recommendedSong.cover),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(25.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(18.dp))

        Column (horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(10.dp)){
            Text(
                text = recommendedSong.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            )

            Text(
                text = recommendedSong.artist,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White.copy(0.8f)
                )
            )

            Row {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = recommendedSong.likeCount,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(0.6f)
                    )
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RecommendedSongItemPrev() {
    MusicPlayerPracticeTheme {
        RecommendedSongItem(RecommendedSong("Not Like Us", "Kendric lamar","554k",R.drawable.not_like_us))
    }
}