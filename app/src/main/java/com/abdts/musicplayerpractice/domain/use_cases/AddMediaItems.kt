package com.abdts.musicplayerpractice.domain.use_cases

import com.abdts.musicplayerpractice.common.Resources
import com.abdts.musicplayerpractice.domain.repository.AudioRepository
import com.abdts.musicplayerpractice.domain.service.VibAudioController
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class AddMediaItems(
    private val vibAudioController: VibAudioController,
    private val audioRepository: AudioRepository
) {

    operator fun invoke()  = flow<Resources<Unit>> {
        val songs = audioRepository.getAudioList()
        vibAudioController.addMediaItems(songs)

        emit(Resources.Success(data = Unit))
    }.onStart {
        emit(Resources.Loading())
    }.catch {
        emit(Resources.Error(message = it.message.toString()))
    }
}