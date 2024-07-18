package com.abdts.musicplayerpractice.domain.use_cases

import com.abdts.musicplayerpractice.common.Resources
import com.abdts.musicplayerpractice.domain.service.VibAudioController
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class PlayAudio(
    private val vibAudioController: VibAudioController,
) {

    operator fun invoke(mediaItemIndex:Int)  = flow<Resources<Unit>> {
        vibAudioController.play(mediaItemIndex)

        emit(Resources.Success(data = Unit))
    }.onStart {
        emit(Resources.Loading())
    }.catch {
        emit(Resources.Error(message = it.message.toString()))
    }
}