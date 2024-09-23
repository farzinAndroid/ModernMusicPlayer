
package com.farzin.core_media_service.util

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.RequestMetadata
import androidx.media3.common.MediaMetadata
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

internal fun buildPlayableMediaItem(
    mediaId: String,
    artistId: Long,
    albumId: Long,
    albumTitle: String,
    mediaUri: Uri,
    artworkUri: Uri,
    title: String,
    artist: String,
    folder: String,
    duration: Long,
    date: LocalDateTime
) = MediaItem.Builder()
    .setMediaId(mediaId)
    .setRequestMetadata(
        RequestMetadata.Builder()
            .setMediaUri(mediaUri)
            .build()
    )
    .setMediaMetadata(
        MediaMetadata.Builder()
            .setArtworkUri(artworkUri)
            .setTitle(title)
            .setArtist(artist)
            .setAlbumTitle(albumTitle)
            .setIsBrowsable(false)
            .setIsPlayable(true)
            .setExtras(
                bundleOf(
                    ARTIST_ID to artistId,
                    ALBUM_ID to albumId,
                    FOLDER to folder,
                    DURATION to duration,
                    DATE to date.toInstant(TimeZone.currentSystemDefault()).epochSeconds,
                )
            )
            .build()
    )
    .build()

internal const val ARTIST_ID = "artist_id"
internal const val ALBUM_ID = "album_id"
internal const val FOLDER = "folder"
internal const val DURATION = "duration"
internal const val DATE = "date"
internal const val IS_FAVORITE_ID = "favorite_id"
