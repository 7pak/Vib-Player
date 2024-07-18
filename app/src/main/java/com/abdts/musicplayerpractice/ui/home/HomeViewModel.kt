package com.abdts.musicplayerpractice.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdts.musicplayerpractice.common.Resources
import com.abdts.musicplayerpractice.domain.use_cases.VibAudioUseCases
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val vibAudioUseCases: VibAudioUseCases
) : ViewModel() {

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    fun onEvent(event: HomeEvent) {

        when (event) {
            HomeEvent.FetchSong -> fetchSong()
            is HomeEvent.OnSongSelected -> homeUiState =
                homeUiState.copy(selectedSong = event.selectedSong)

            HomeEvent.PauseSong -> pauseSong()
            HomeEvent.PlaySong -> playSong()
            HomeEvent.ResumeSong -> resumeSong()
            HomeEvent.SkipToNextSong -> skipToNext()
            HomeEvent.SkipToPreviousSong -> skipToPrevious()
        }
    }

    private fun fetchSong() {
        viewModelScope.launch {
            vibAudioUseCases.getAudios().collectLatest { resource ->
                when (resource) {
                    is Resources.Error -> {
                        homeUiState =
                            homeUiState.copy(errorMessage = resource.message, loading = false)
                    }

                    is Resources.Loading -> {
                        homeUiState =
                            homeUiState.copy(errorMessage = resource.message, loading = true)
                    }

                    is Resources.Success -> {
                        vibAudioUseCases.addMediaItems()
                        homeUiState = homeUiState.copy(songs = resource.data, loading = false)
                    }
                }
            }
        }
    }

    private fun pauseSong() {
        vibAudioUseCases.pauseAudio()
    }

    private fun playSong() {
        homeUiState.apply {
            songs?.indexOf(selectedSong)?.let {
                vibAudioUseCases.playAudio(it)
            }
        }
    }

    private fun resumeSong() {
        vibAudioUseCases.resumeAudio()
    }

    private fun skipToNext() {
        vibAudioUseCases.skipToNextAudio {
            it?.let {
                homeUiState = homeUiState.copy(selectedSong = it)
            }
        }
    }

    private fun skipToPrevious() {
        vibAudioUseCases.skipToPreviousAudio {
            it?.let {
                homeUiState = homeUiState.copy(selectedSong = it)
            }
        }
    }


}