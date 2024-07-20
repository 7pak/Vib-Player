package com.abdts.musicplayerpractice.ui.home

import android.net.Uri
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.abdts.musicplayerpractice.common.PlayerEvents
import com.abdts.musicplayerpractice.common.VibAudioState
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.data.service.VibAudioServiceHandler
import com.abdts.musicplayerpractice.domain.repository.AudioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
@OptIn(SavedStateHandleSaveableApi::class)

class HomeViewModel(
    private val audioRepository: AudioRepository,
    private val vibAudioServiceHandler: VibAudioServiceHandler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var duration by savedStateHandle.saveable {
        mutableLongStateOf(0L)
    }
    var progress by savedStateHandle.saveable {
        mutableFloatStateOf(0f)
    }
    private var progressString by savedStateHandle.saveable {
        mutableStateOf("00:00")
    }
    var isPlaying by savedStateHandle.saveable {
        mutableStateOf(false)
    }
    var currentSelectedAudio by savedStateHandle.saveable {
        mutableStateOf(Audio(Uri.EMPTY, 0L, "", "", "", 0, "", Uri.EMPTY))
    }
    var audioList by savedStateHandle.saveable {
        mutableStateOf(listOf<Audio>())
    }

    private var _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Initial)
    val uiState: StateFlow<UIState> = _uiState


    init {
        loadAudioData()
    }
    init {
        viewModelScope.launch {
            vibAudioServiceHandler.audioState.collectLatest { mediaState ->
                when (mediaState) {
                    is VibAudioState.Buffering -> calculateProgressValue(mediaState.progress)
                    is VibAudioState.CurrentPlaying -> currentSelectedAudio = audioList[mediaState.mediaItemIndex]
                    VibAudioState.Initial -> _uiState.value = UIState.Initial
                    is VibAudioState.Playing -> isPlaying = mediaState.isPlaying
                    is VibAudioState.Progress -> calculateProgressValue(mediaState.progress)
                    is VibAudioState.Ready -> {
                        duration = mediaState.duration
                        _uiState.value = UIState.Ready
                    }
                }
            }
        }
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
        progress = if (currentProgress > 0) {
            (currentProgress.toFloat() / duration.toFloat()) * 100f
        } else {
            0f
        }
        progressString = formatDuration(currentProgress)
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
            is UIEvents.SeekTo -> vibAudioServiceHandler.onPlayerEvent(PlayerEvents.SeekTo, seekPosition = (( duration * uiEvents.position) /100f).toLong() )
            is UIEvents.SelectedAudioChange -> vibAudioServiceHandler.onPlayerEvent(PlayerEvents.SelectedAudioChange, selectedAudioIndex = uiEvents.index)
            is UIEvents.UpdateProgress -> {
                vibAudioServiceHandler.onPlayerEvent(PlayerEvents.UpdateProgress(uiEvents.progress))
                progress = uiEvents.progress
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