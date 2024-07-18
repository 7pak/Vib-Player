package com.abdts.musicplayerpractice.ui.home

import com.abdts.musicplayerpractice.data.local.model.Audio


data class HomeUiState(
    val loading: Boolean? = false,
    val songs: List<Audio>? = emptyList(),
    val selectedSong: Audio? = null,
    val errorMessage: String? = null
)
