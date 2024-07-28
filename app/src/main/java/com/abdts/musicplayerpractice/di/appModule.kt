package com.abdts.musicplayerpractice.di

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import com.abdts.musicplayerpractice.data.local.ContentResolverHelper
import com.abdts.musicplayerpractice.data.notification.VibAudioNotificationManager
import com.abdts.musicplayerpractice.data.repository.AudioRepositoryImpl
import com.abdts.musicplayerpractice.data.service.VibAudioServiceHandler
import com.abdts.musicplayerpractice.domain.repository.AudioRepository
import com.abdts.musicplayerpractice.ui.local.LocalViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

@UnstableApi
val appModule = module {
    single {
        ContentResolverHelper(context = androidApplication())
    }

    single {
        AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()
    }

    single<ExoPlayer> {
        ExoPlayer.Builder(androidApplication()).apply {
            setAudioAttributes(get(), true)
            setHandleAudioBecomingNoisy(true)
            setTrackSelector(DefaultTrackSelector(get<Context>()))
        }.build()
    }

    single {
        MediaSession.Builder(get<Context>(), get<ExoPlayer>()).build()
    }

    single {
        VibAudioNotificationManager(get<Context>(), get())
    }

    single {
        VibAudioServiceHandler(get())
    }

    single<AudioRepository> {
        AudioRepositoryImpl(get())
    }

    viewModelOf(::LocalViewModel)
}