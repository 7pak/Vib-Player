package com.abdts.musicplayerpractice.ui.remote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.abdts.musicplayerpractice.ui.remote.items.ArtistItem
import com.abdts.musicplayerpractice.ui.remote.items.RecommendedSongItem
import com.abdts.musicplayerpractice.ui.remote.items.VibRemoteTopAppBar
import com.abdts.musicplayerpractice.ui.theme.LightBlack
import com.abdts.musicplayerpractice.ui.theme.MusicPlayerPracticeTheme

@Composable
fun RemoteAudioScreen(navController: NavHostController) {


    Scaffold(
        containerColor = LightBlack,
        topBar = {
            VibRemoteTopAppBar(audios = emptyList(),navController) {
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(innerPadding)
        ) {

            Text(
                text = "Artists",
                style = MaterialTheme.typography.displaySmall.copy(color = Color.White.copy(0.9f))
            )
            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(RemoteMockData.artistList){artist->
                    ArtistItem(artist = artist)
                }
            }

            Spacer(modifier = Modifier.height(26.dp))
            Text(
                text = "Recommended for you",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White.copy(0.9f))
            )
            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(18.dp)) {
                items(RemoteMockData.recommendedSongList){recommendedSong ->
                    RecommendedSongItem(recommendedSong = recommendedSong)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RemoteAudioScreenPreview() {
    MusicPlayerPracticeTheme {
        RemoteAudioScreen(rememberNavController())
    }
}