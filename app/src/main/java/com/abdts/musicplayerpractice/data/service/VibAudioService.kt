package com.abdts.musicplayerpractice.data.service

import android.content.Intent
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.abdts.musicplayerpractice.data.notification.VibAudioNotificationManager
import org.koin.android.ext.android.inject

class VibAudioService:MediaSessionService() {
    val mediaSession by inject<MediaSession>()

    val notificationManager by inject<VibAudioNotificationManager>()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationManager.starNotificationService(mediaSession = mediaSession, mediaSessionService = this)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession = mediaSession
    override fun onDestroy() {
        mediaSession.apply {
            if (player.playbackState != Player.STATE_IDLE){
                player.seekTo(0)
                player.playWhenReady = false
                player.stop()
            }

        }
        super.onDestroy()

    }
}