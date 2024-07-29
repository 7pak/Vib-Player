package com.abdts.musicplayerpractice.ui.remote

import androidx.annotation.DrawableRes
import com.abdts.musicplayerpractice.R

object RemoteMockData {

    val artistList = listOf(
        Artist("Travis", R.drawable.travis),
        Artist("Kendrick", R.drawable.kendric_lahmar),
        Artist("Dua", R.drawable.dua_lipa),
        Artist("Drake", R.drawable.drake),
        Artist("Tylor", R.drawable.tylor_swift),
        Artist("Ice", R.drawable.travis),
    )

    val recommendedSongList = listOf(
        RecommendedSong("All eyes on me","Tupac","3.2M",R.drawable.all_eyes_on_me),
        RecommendedSong("Levitating","Dua Lipa & Da Baby","144.8k",R.drawable.levitating),
        RecommendedSong("Not Like Us","Kendrick Lamar","800k",R.drawable.not_like_us),
        RecommendedSong("Shotta Flow","NLE Choppa","585.9k",R.drawable.shout_out),
    )
}

data class Artist(
        val name:String,
    @DrawableRes val avatar:Int
 )

data class RecommendedSong(
    val name:String,
    val artist:String,
    val likeCount:String,
    @DrawableRes val cover:Int
)