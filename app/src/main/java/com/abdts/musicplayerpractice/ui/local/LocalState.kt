package com.abdts.musicplayerpractice.ui.local

import android.net.Uri
import com.abdts.musicplayerpractice.data.local.model.Audio

data class LocalState(
    val ready: Boolean = false,
    val duration: Long = 0L,
    val progress: Float = 0f,
    val progressString: String = "00:00",
    val isPlaying: Boolean = false,
    val currentSelectedAudio: Audio = Audio(Uri.EMPTY, 0L, "", "", "", 0, "", Uri.EMPTY),
)
