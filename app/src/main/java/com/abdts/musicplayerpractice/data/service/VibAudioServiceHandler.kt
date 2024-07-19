package com.abdts.musicplayerpractice.data.service

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.abdts.musicplayerpractice.common.PlayerEvents
import com.abdts.musicplayerpractice.common.VibAudioState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VibAudioServiceHandler(
    private val exoPlayer: ExoPlayer
) : Player.Listener {

    private val scope = MainScope()

    private var _audioState: MutableStateFlow<VibAudioState> =
        MutableStateFlow(VibAudioState.Initial)
    val audioState: StateFlow<VibAudioState> = _audioState

    var job:Job? = null

    init {
        exoPlayer.addListener(this)
    }
    fun addMediaItem(mediaItem: MediaItem){
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

    fun setMediaItemList(mediaItems: List<MediaItem>){
        exoPlayer.setMediaItems(mediaItems)
        exoPlayer.prepare()
    }

    suspend fun onPlayerEvent(playerEvent: PlayerEvents,selectedAudioIndex:Int = -1,seekPosition:Long = 0){
         when(playerEvent){
             PlayerEvents.Backward -> exoPlayer.seekBack()
             PlayerEvents.Forward -> exoPlayer.seekForward()
             PlayerEvents.PlayPause -> playOrPause()
             PlayerEvents.SeekTo -> exoPlayer.seekTo(seekPosition)
             PlayerEvents.SeekToNext -> exoPlayer.seekToNext()
             PlayerEvents.SeekToPrevious -> exoPlayer.seekToPrevious()
             PlayerEvents.SelectedAudioChange -> {
                 when(selectedAudioIndex){
                     exoPlayer.currentMediaItemIndex  -> {
                         playOrPause()
                     }
                     else -> {
                         exoPlayer.seekToDefaultPosition(selectedAudioIndex)
                          _audioState.value = VibAudioState.Playing(isPlaying = true)
                         exoPlayer.playWhenReady = true
                         startProgressUpdate()
                     }
                 }
             }
             is PlayerEvents.UpdateProgress -> {
                 exoPlayer.seekTo(
                     (exoPlayer.duration * playerEvent.progress.toLong())
                 )
                // _audioState.value = VibAudioState.Progress(progress = playerEvent.progress.toLong())
             }
             PlayerEvents.Stop -> stopProgressUpdate()
         }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {

        when(playbackState){
            ExoPlayer.STATE_BUFFERING -> _audioState.value = VibAudioState.Buffering(progress = exoPlayer.currentPosition)
            ExoPlayer.STATE_READY -> _audioState.value = VibAudioState.Ready(duration = exoPlayer.duration)

        }
        super.onPlaybackStateChanged(playbackState)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {

        _audioState.value = VibAudioState.Playing(isPlaying = isPlaying)
        _audioState.value = VibAudioState.CurrentPlaying(mediaItemIndex = exoPlayer.currentMediaItemIndex)

        if (isPlaying){
            scope.launch(Dispatchers.Main) {
                startProgressUpdate()
            }
        }else{
            stopProgressUpdate()
        }
        super.onIsPlayingChanged(isPlaying)
    }

    private suspend fun playOrPause(){
        if (exoPlayer.isPlaying){
            exoPlayer.pause()
            stopProgressUpdate()
        }else {
            exoPlayer.play()
            _audioState.value = VibAudioState.Playing(isPlaying = true)
            startProgressUpdate()
        }
    }

    private suspend fun startProgressUpdate() = job.run {
        while (true){
            delay(500)
            _audioState.value = VibAudioState.Progress(progress = exoPlayer.contentPosition)
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()
        _audioState.value = VibAudioState.Playing(isPlaying = false)
    }
}