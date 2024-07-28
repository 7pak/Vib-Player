package com.abdts.musicplayerpractice.ui

sealed class UIEvents {

    object PlayPause: UIEvents()
    data class SelectedAudioChange(val index:Int): UIEvents()
    data class SeekTo(val position:Float): UIEvents()
    object SeekToNext: UIEvents()
    object SeekToPrevious: UIEvents()
    object Forward: UIEvents()
    object Backward: UIEvents()
    data class UpdateProgress(val progress:Float): UIEvents()
}