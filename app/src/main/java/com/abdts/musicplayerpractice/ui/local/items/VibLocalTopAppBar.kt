package com.abdts.musicplayerpractice.ui.local.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.navigation.Screens
import com.abdts.musicplayerpractice.ui.local.LocalState
import com.abdts.musicplayerpractice.ui.theme.MusicPlayerPracticeTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VibLocalTopAppBar(
    audios: List<Audio>,
    navController: NavHostController,
    localState: LocalState,
    onItemClicked: (Int, LocalState) -> Unit,
    ) {

    var searchText by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }

    var searchList by remember {
        mutableStateOf(listOf<Audio>())
    }


    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
            .background(MaterialTheme.colorScheme.primary).padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Vib-Player",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = {navController.navigate(Screens.SettingAudioScreen.route)},
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        SearchBar(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 12.dp),
            shape = CircleShape,
            colors = SearchBarDefaults.colors(containerColor = Color.White),
            query = searchText,
            onQueryChange = {
                searchText = it
            },
            onSearch = { searchQuery ->
                searchList =
                    audios.filter { it.displayName.lowercase().contains(searchQuery.lowercase()) }.toList()
            },
            active = active, onActiveChange = { active = it },
            placeholder = {
                Text(text = "Search for audio...", color = Color.Gray)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            trailingIcon = {
                if (active) {
                    IconButton(onClick = {
                        if (searchText.isEmpty()) {
                            active = false
                            return@IconButton
                        }
                        searchText = ""
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                    }

                }
            }
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    if (searchList.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No Audio found")
                        }
                    }
                }
                itemsIndexed(searchList) {index,audio->
                    AudioItem(audio = audio, isSelected = false, isPlaying = false) {
                        onItemClicked(
                            audios.indexOfFirst {
                                it.id==audio.id
                            },
                            localState.copy(
                                currentSelectedAudio = audio,
                                isPlaying = !localState.isPlaying
                            )
                        )
                        active = false
                        searchText = ""
                    }
                }

            }
        }
    }
}


@Preview
@Composable
private fun HomeAppBarPreview() {
    MusicPlayerPracticeTheme {
        VibLocalTopAppBar(emptyList(), localState = LocalState(), navController = rememberNavController(), onItemClicked = { _, _->})
    }
}