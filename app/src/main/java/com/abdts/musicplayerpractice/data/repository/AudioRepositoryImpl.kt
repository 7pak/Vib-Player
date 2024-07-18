package com.abdts.musicplayerpractice.data.repository

import com.abdts.musicplayerpractice.data.local.ContentResolverHelper
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.domain.repository.AudioRepository

class AudioRepositoryImpl(
    private val contentResolver : ContentResolverHelper
):AudioRepository {
    override suspend fun getAudioList(): List<Audio> {
        return contentResolver.getAudioList()
    }
}