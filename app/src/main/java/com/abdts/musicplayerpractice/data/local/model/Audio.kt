package com.abdts.musicplayerpractice.data.local.model

import android.net.Uri

data class Audio(
    val uri: Uri,
    val id:Long,
    val displayName:String,
    val artist:String,
    val data:String,
    val duration:Int,
    val title:String,
    val albumArtUri:Uri
)
