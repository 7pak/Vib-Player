package com.abdts.musicplayerpractice.data.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerNotificationManager

class NotificationActionReceiver:BroadcastReceiver() {
    companion object {
        @UnstableApi
        val intentFilter = IntentFilter().apply {
            addAction(PlayerNotificationManager.ACTION_NEXT)
            addAction(PlayerNotificationManager.ACTION_PREVIOUS)
            addAction(PlayerNotificationManager.ACTION_PAUSE)
            addAction(PlayerNotificationManager.ACTION_STOP)
        }
    }

    @OptIn(UnstableApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            PlayerNotificationManager.ACTION_NEXT -> {
            }
            PlayerNotificationManager.ACTION_PREVIOUS -> {
            }
            PlayerNotificationManager.ACTION_PAUSE -> {
            }
            PlayerNotificationManager.ACTION_STOP -> {

            }
        }
    }
}