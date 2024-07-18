package com.abdts.musicplayerpractice.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdts.musicplayerpractice.common.AudioControllerUiState
import com.abdts.musicplayerpractice.common.PlayerState
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.domain.use_cases.VibAudioUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class SharedViewModel(
    private val vibAudioUseCases: VibAudioUseCases
) : ViewModel() {

    var audioControllerUiState by mutableStateOf(AudioControllerUiState())
        private set

    init {
        setMediaControllerCallback()
    }

    private fun setMediaControllerCallback() {
        vibAudioUseCases.setMediaControllerCallback { playerState: PlayerState, currentSong: Audio?, currentPosition: Long, totalDuration: Long, isShuffleEnabled: Boolean, isRepeatOneEnabled: Boolean ->
            audioControllerUiState = audioControllerUiState.copy(
                playerState = playerState,
                currentSong = currentSong,
                currentPosition = currentPosition,
                totalDuration = totalDuration,
                isShuffleEnabled = isShuffleEnabled,
                isRepeatOneEnabled = isRepeatOneEnabled
            )

            if (playerState == PlayerState.PLAYING) {
                viewModelScope.launch {
                    while (true) {
                        delay(3.seconds)
                        audioControllerUiState = audioControllerUiState.copy(
                            currentPosition = vibAudioUseCases.getCurrentSongPosition().firstOrNull()?.data ?: 0L
                        )
                    }
                }
            }
        }
    }

    fun destroyMediaController() {
        vibAudioUseCases.destroyMediaController()
    }
}