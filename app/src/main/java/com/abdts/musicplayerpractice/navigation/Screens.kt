package com.abdts.musicplayerpractice.navigation

import kotlinx.serialization.Serializable
@Serializable
sealed class Screens (val route:String){

    @Serializable
    data object LocalAudioScreen:Screens("local")

    @Serializable
    data object RemoteAudioScreen:Screens("remote")

    @Serializable
    data object SettingAudioScreen:Screens("setting")
}