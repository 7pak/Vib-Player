package com.abdts.musicplayerpractice.ui.home

sealed class UIState {
    object Initial:UIState()
    object Ready:UIState()
}