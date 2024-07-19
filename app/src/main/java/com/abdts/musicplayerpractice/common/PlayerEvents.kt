package com.abdts.musicplayerpractice.common

sealed class PlayerEvents {

    object PlayPause:PlayerEvents()
    object SelectedAudioChange:PlayerEvents()
    object Forward:PlayerEvents()
    object Backward:PlayerEvents()
    object SeekTo:PlayerEvents()
    object SeekToNext:PlayerEvents()
    object SeekToPrevious:PlayerEvents()
    object Stop:PlayerEvents()
    data class UpdateProgress(val progress:Float):PlayerEvents()
}