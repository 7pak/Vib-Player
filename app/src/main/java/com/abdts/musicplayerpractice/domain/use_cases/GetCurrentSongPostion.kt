package com.abdts.musicplayerpractice.domain.use_cases

import com.abdts.musicplayerpractice.common.Resources
import com.abdts.musicplayerpractice.domain.service.VibAudioController
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetCurrentSongPostion(
    private val vibAudioController: VibAudioController,
) {

    operator fun invoke()  = flow<Resources<Long>> {
        val currentPosition = vibAudioController.getCurrentPosition()

        emit(Resources.Success(data = currentPosition))
    }.onStart {
        emit(Resources.Loading())
    }.catch {
        emit(Resources.Error(message = it.message.toString()))
    }
}