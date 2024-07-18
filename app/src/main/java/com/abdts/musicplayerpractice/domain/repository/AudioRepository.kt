package com.abdts.musicplayerpractice.domain.repository

import com.abdts.musicplayerpractice.data.local.model.Audio

interface AudioRepository {
    suspend fun getAudioList(): List<Audio>
}