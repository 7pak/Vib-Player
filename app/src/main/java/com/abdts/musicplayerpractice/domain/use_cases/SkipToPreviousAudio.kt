package com.abdts.musicplayerpractice.domain.use_cases

import com.abdts.musicplayerpractice.common.Resources
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.domain.repository.AudioRepository
import com.abdts.musicplayerpractice.domain.service.VibAudioController
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class SkipToPreviousAudio(
    private val vibAudioController: VibAudioController,
) {

    operator fun invoke(updateUi:(Audio?)->Unit)  = flow<Resources<Unit>> {
        vibAudioController.skipToPreviousSong()
        updateUi(vibAudioController.getCurrentSong())

        emit(Resources.Success(data = Unit))
    }.onStart {
        emit(Resources.Loading())
    }.catch {
        emit(Resources.Error(message = it.message.toString()))
    }
}