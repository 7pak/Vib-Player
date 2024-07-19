package com.abdts.musicplayerpractice.common

sealed class VibAudioState {

    object Initial:VibAudioState()

    data class Ready(val duration:Long):VibAudioState()

    data class Progress(val progress:Long):VibAudioState()

    data class Buffering(val progress: Long):VibAudioState()

    data class Playing(val isPlaying:Boolean):VibAudioState()

    data class CurrentPlaying(val mediaItemIndex:Int):VibAudioState()
}