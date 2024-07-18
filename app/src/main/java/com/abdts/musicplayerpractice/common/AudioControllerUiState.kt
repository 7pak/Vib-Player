package com.abdts.musicplayerpractice.common

import com.abdts.musicplayerpractice.data.local.model.Audio

data class AudioControllerUiState(
    val playerState: PlayerState? = null,
    val currentSong: Audio? = null,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val isShuffleEnabled: Boolean = false,
    val isRepeatOneEnabled: Boolean = false
)
