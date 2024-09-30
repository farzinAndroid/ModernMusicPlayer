

package com.farzin.core_media_service.mapper

import com.farzin.core_media_service.util.buildPlayableMediaItem
import com.farzin.core_model.Song

internal fun Song.asMediaItem() = buildPlayableMediaItem(
    mediaId = mediaId,
    artistId = artistId,
    albumId = albumId,
    mediaUri = mediaUri,
    artworkUri = artworkUri,
    title = title,
    artist = artist,
    folder = folder,
    duration = duration,
    date = date,
    albumTitle = album
)
