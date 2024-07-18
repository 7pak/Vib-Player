package com.abdts.musicplayerpractice.data.mapper

import android.media.MediaMetadata
import android.net.Uri
import androidx.media3.common.MediaItem
import com.abdts.musicplayerpractice.data.local.model.Audio

fun MediaItem.toAudio(): Audio {
    val mediaMetadata = this.mediaMetadata
    return Audio(
        uri = this.localConfiguration?.uri ?: Uri.EMPTY,
        id = this.mediaId.toLongOrNull() ?: 0L,
        displayName = mediaMetadata.displayTitle?.toString() ?: "",
        artist = mediaMetadata.artist?.toString() ?: "",
        data = this.localConfiguration?.uri?.path ?: "",
        duration = mediaMetadata.extras?.getLong(MediaMetadata.METADATA_KEY_DURATION, 0L)?.toInt() ?: 0,
        title = mediaMetadata.title?.toString() ?: "",
        albumArtUri = mediaMetadata.artworkUri ?: Uri.EMPTY
    )
}
