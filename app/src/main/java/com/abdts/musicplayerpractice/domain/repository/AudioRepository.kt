package com.abdts.musicplayerpractice.domain.repository

import com.abdts.musicplayerpractice.data.local.model.Audio

interface AudioRepository {
    suspend fun getLocalAudioList(): List<Audio>

    suspend fun getRemoteAudioList():List<Audio>
}