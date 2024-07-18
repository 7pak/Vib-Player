package com.abdts.musicplayerpractice.domain.service

import com.abdts.musicplayerpractice.common.PlayerState
import com.abdts.musicplayerpractice.data.local.model.Audio

interface VibAudioController {
    var mediaControllerCallback: (
        (
        playerState: PlayerState,
        currentMusic: Audio?,
        currentPosition: Long,
        totalDuration: Long,
        isShuffleEnabled: Boolean,
        isRepeatOneEnabled: Boolean
    ) -> Unit
    )?

    fun addMediaItems(songs: List<Audio>)

    fun play(mediaItemIndex: Int)

    fun resume()

    fun pause()

    fun getCurrentPosition(): Long

    fun destroy()

    fun skipToNextSong()

    fun skipToPreviousSong()

    fun getCurrentSong(): Audio?

    fun seekTo(position: Long)
}