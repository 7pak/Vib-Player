package com.abdts.musicplayerpractice.domain.use_cases

data class VibAudioUseCases(
    val addMediaItems: AddMediaItems,
    val destroyMediaController: DestroyMediaController,
    val getAudios: GetAudios,
    val getCurrentSongPosition : GetCurrentSongPostion,
    val pauseAudio : PauseAudio,
    val playAudio : PlayAudio,
    val resumeAudio : ResumeAudio,
    val seekAudioTo : SeekAudioTo,
    val setMediaControllerCallback : SetMediaControllerCallback,
    val skipToNextAudio : SkipToNextAudio,
    val skipToPreviousAudio : SkipToPreviousAudio,
)