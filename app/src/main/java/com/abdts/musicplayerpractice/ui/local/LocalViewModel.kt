package com.abdts.musicplayerpractice.ui.local

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.abdts.musicplayerpractice.common.PlayerEvents
import com.abdts.musicplayerpractice.common.VibAudioState
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.data.service.VibAudioServiceHandler
import com.abdts.musicplayerpractice.domain.repository.AudioRepository
import com.abdts.musicplayerpractice.ui.UIEvents
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class LocalViewModel(
    private val audioRepository: AudioRepository,
    private val vibAudioServiceHandler: VibAudioServiceHandler,
) : ViewModel() {




    var localState by mutableStateOf(LocalState())
        private set

    var audioList:List<Audio> = emptyList()
        private set


    init {
        loadAudioData()
        setAudioStates()
    }

    fun updateState(state: LocalState){
        localState = state.copy()
    }

    private fun loadAudioData(){
        viewModelScope.launch {
            val audios = audioRepository.getAudioList().filter { it.duration>0 }
                .sortedBy {
                    it.displayName
                }
            audioList = audios

            setMediaItems()
        }
    }

    private fun setAudioStates(){
        viewModelScope.launch {
            vibAudioServiceHandler.audioState.collectLatest { mediaState ->
                when (mediaState) {
                    is VibAudioState.Buffering -> calculateProgressValue(mediaState.progress)
                    is VibAudioState.CurrentPlaying -> localState = localState.copy(currentSelectedAudio = audioList[mediaState.mediaItemIndex])
                    VibAudioState.Initial -> {}
                    is VibAudioState.Playing -> localState = localState.copy(isPlaying = mediaState.isPlaying)
                    is VibAudioState.Progress -> calculateProgressValue(mediaState.progress)
                    is VibAudioState.Ready -> {
                        localState = localState.copy(duration = mediaState.duration)
                    }
                }
            }
        }
    }

    private fun setMediaItems(){
        audioList.map {audio ->
            MediaItem.Builder()
                .setUri(audio.uri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setAlbumArtist(audio.artist)
                        .setDisplayTitle(audio.title)
                        .setSubtitle(audio.displayName)
                        .build()
                )
                .build()
        }.also {
            vibAudioServiceHandler.setMediaItemList(it)
        }
    }

    private fun calculateProgressValue(currentProgress: Long) {
        val progress = if (currentProgress > 0) {
            (currentProgress.toFloat() / localState.duration.toFloat()) * 100f
        } else {
            0f
        }
        localState = localState.copy(progress = progress)
        localState = localState.copy(progressString = formatDuration(currentProgress))
    }

    private fun formatDuration(duration: Long): String {
        val minute = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds = (minute) - minute * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)
        return String.format("%02d:%02d", minute, seconds)
    }


    fun onUIEvent(uiEvents: UIEvents) = viewModelScope.launch {
        when(uiEvents){
            UIEvents.Backward -> vibAudioServiceHandler.onPlayerEvent(PlayerEvents.Backward)
            UIEvents.Forward -> vibAudioServiceHandler.onPlayerEvent(PlayerEvents.Forward)
            UIEvents.PlayPause -> vibAudioServiceHandler.onPlayerEvent(PlayerEvents.PlayPause)
            UIEvents.SeekToNext -> vibAudioServiceHandler.onPlayerEvent(PlayerEvents.SeekToNext)
            UIEvents.SeekToPrevious -> vibAudioServiceHandler.onPlayerEvent(PlayerEvents.SeekToPrevious)
            is UIEvents.SeekTo -> vibAudioServiceHandler.onPlayerEvent(PlayerEvents.SeekTo, seekPosition = (( localState.duration * uiEvents.position) /100f).toLong() )
            is UIEvents.SelectedAudioChange -> vibAudioServiceHandler.onPlayerEvent(PlayerEvents.SelectedAudioChange, selectedAudioIndex = uiEvents.index)
            is UIEvents.UpdateProgress -> {
                vibAudioServiceHandler.onPlayerEvent(PlayerEvents.UpdateProgress(uiEvents.progress))
                localState = localState.copy(progress = uiEvents.progress)
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            vibAudioServiceHandler.onPlayerEvent(PlayerEvents.Stop)
        }
        super.onCleared()
    }
}