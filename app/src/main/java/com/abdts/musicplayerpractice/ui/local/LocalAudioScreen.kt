package com.abdts.musicplayerpractice.ui.local

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.ui.UIEvents
import com.abdts.musicplayerpractice.ui.local.items.AudioDetailBottomSheet
import com.abdts.musicplayerpractice.ui.local.items.AudioItem
import com.abdts.musicplayerpractice.ui.local.items.VibLocalTopAppBar
import com.abdts.musicplayerpractice.ui.theme.MusicPlayerPracticeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalAudioScreen(
    audioList: List<Audio>,
    localState: LocalState,
    onEvent: (UIEvents) -> Unit,
    onItemClicked: (Int, LocalState) -> Unit,
    navController: NavHostController
) {

    var isBottomSheetExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()



    Scaffold(
        topBar = {
            VibLocalTopAppBar(
                audioList,
                navController = navController,
                localState,
                onItemClicked = onItemClicked
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding, modifier = Modifier
                .fillMaxSize()
        ) {
            itemsIndexed(audioList) { index, audio ->
                AudioItem(
                    audio = audio,
                    onClick = { isPlaying ->
                        if (isPlaying && localState.currentSelectedAudio.id == audio.id) {
                            isBottomSheetExpanded = true
                        } else {
                            onItemClicked(
                                index,
                                localState.copy(
                                    currentSelectedAudio = audio,
                                    isPlaying = !localState.isPlaying
                                )
                            )
                        }

                    },
                    isSelected = audio.id == localState.currentSelectedAudio.id,
                    isPlaying = localState.isPlaying
                )
                HorizontalDivider()
            }
        }
    }

    if (isBottomSheetExpanded) {
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetExpanded = false },
            sheetState = sheetState,
            modifier = Modifier.fillMaxWidth(0.95f),

            content = {
                AudioDetailBottomSheet(
                    localState = localState,
                    onEvent = onEvent
                )
            }
        )
    }
}

@Preview
@Composable
private fun HomeScreenPrev() {
    MusicPlayerPracticeTheme {
        LocalAudioScreen(
            localState = LocalState(),
            onEvent = {},
            audioList = emptyList(),
            onItemClicked = { _, _ -> },
            navController = rememberNavController()
        )
    }
}