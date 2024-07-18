package com.abdts.musicplayerpractice.domain.use_cases

import com.abdts.musicplayerpractice.common.PlayerState
import com.abdts.musicplayerpractice.common.Resources
import com.abdts.musicplayerpractice.data.local.model.Audio
import com.abdts.musicplayerpractice.domain.repository.AudioRepository
import com.abdts.musicplayerpractice.domain.service.VibAudioController
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class SetMediaControllerCallback(
    private val vibAudioController: VibAudioController,
) {

    operator fun invoke(callback: (
        playerState: PlayerState,
        currentSong: Audio?,
        currentPosition: Long,
        totalDuration: Long,
        isShuffleEnabled: Boolean,
        isRepeatOneEnabled: Boolean
    ) -> Unit)  = flow<Resources<Unit>> {

        vibAudioController.mediaControllerCallback = callback

        emit(Resources.Success(data = Unit))
    }.onStart {
        emit(Resources.Loading())
    }.catch {
        emit(Resources.Error(message = it.message.toString()))
    }
}