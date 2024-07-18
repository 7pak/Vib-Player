package com.abdts.musicplayerpractice.navigation

import kotlinx.serialization.Serializable
@Serializable
sealed class Screens {

    @Serializable
    data object HomeScreen:Screens()

    @Serializable
    data class AudioDetailScreen(val data:String?):Screens()
}