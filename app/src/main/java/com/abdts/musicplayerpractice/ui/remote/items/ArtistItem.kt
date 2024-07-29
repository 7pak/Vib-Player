package com.abdts.musicplayerpractice.ui.remote.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.abdts.musicplayerpractice.ui.remote.Artist
import com.abdts.musicplayerpractice.ui.theme.MusicPlayerPracticeTheme

@Composable
fun ArtistItem(
    artist: Artist
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.clip(
        RoundedCornerShape(25.dp)
    ).clickable {  }) {

        Image(
            painter = painterResource(id = artist.avatar),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(25.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = artist.name,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(0.8f)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArtistItemPrev() {
    MusicPlayerPracticeTheme {
        ArtistItem(Artist(name = "ABOOD", avatar = R.drawable.ic_logo))
    }
}