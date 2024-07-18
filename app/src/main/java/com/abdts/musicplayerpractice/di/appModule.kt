package com.abdts.musicplayerpractice.di

import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import com.abdts.musicplayerpractice.data.local.ContentResolverHelper
import com.abdts.musicplayerpractice.data.repository.AudioRepositoryImpl
import com.abdts.musicplayerpractice.data.service.VibAudioControllerImpl
import com.abdts.musicplayerpractice.domain.repository.AudioRepository
import com.abdts.musicplayerpractice.domain.service.VibAudioController
import com.abdts.musicplayerpractice.domain.use_cases.AddMediaItems
import com.abdts.musicplayerpractice.domain.use_cases.DestroyMediaController
import com.abdts.musicplayerpractice.domain.use_cases.GetAudios
import com.abdts.musicplayerpractice.domain.use_cases.GetCurrentSongPostion
import com.abdts.musicplayerpractice.domain.use_cases.PauseAudio
import com.abdts.musicplayerpractice.domain.use_cases.PlayAudio
import com.abdts.musicplayerpractice.domain.use_cases.ResumeAudio
import com.abdts.musicplayerpractice.domain.use_cases.SeekAudioTo
import com.abdts.musicplayerpractice.domain.use_cases.SetMediaControllerCallback
import com.abdts.musicplayerpractice.domain.use_cases.SkipToNextAudio
import com.abdts.musicplayerpractice.domain.use_cases.SkipToPreviousAudio
import com.abdts.musicplayerpractice.domain.use_cases.VibAudioUseCases
import com.abdts.musicplayerpractice.ui.SharedViewModel
import com.abdts.musicplayerpractice.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

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

    single {
        ExoPlayer.Builder(androidApplication()).build().apply {
            setAudioAttributes(get(), true)
            setHandleAudioBecomingNoisy(true)
        }
    }

    single<AudioRepository> {
        AudioRepositoryImpl(get())
    }

    single<VibAudioController> {
        VibAudioControllerImpl(androidApplication())
    }

    single {
        VibAudioUseCases(
            addMediaItems = AddMediaItems(get(), get()),
            destroyMediaController = DestroyMediaController(get()),
            getAudios = GetAudios(get()),
            getCurrentSongPosition = GetCurrentSongPostion(get()),
            pauseAudio = PauseAudio(get()),
            playAudio = PlayAudio(get()),
            resumeAudio = ResumeAudio(get()),
            seekAudioTo = SeekAudioTo(get()),
            setMediaControllerCallback = SetMediaControllerCallback(get()),
            skipToNextAudio = SkipToNextAudio(get()),
            skipToPreviousAudio = SkipToPreviousAudio(get()),
        )
    }

    viewModelOf(::HomeViewModel)
    viewModelOf(::SharedViewModel)
}