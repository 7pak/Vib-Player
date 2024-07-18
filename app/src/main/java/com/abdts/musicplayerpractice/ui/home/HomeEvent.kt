package com.abdts.musicplayerpractice.ui.home

import com.abdts.musicplayerpractice.data.local.model.Audio

sealed class HomeEvent {
    object PlaySong : HomeEvent()
    object PauseSong : HomeEvent()
    object ResumeSong : HomeEvent()
    object FetchSong : HomeEvent()
    object SkipToNextSong : HomeEvent()
    object SkipToPreviousSong : HomeEvent()
    data class OnSongSelected(val selectedSong: Audio) : HomeEvent()
}