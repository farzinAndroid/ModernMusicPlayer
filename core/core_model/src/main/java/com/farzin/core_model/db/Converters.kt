package com.farzin.core_model.db

import androidx.core.net.toUri
import com.farzin.core_model.Song

fun Song.toSongDB() = SongDB(
    title = this.title,
    artist = this.artist,
    album = this.album,
    duration = this.duration,
    mediaUri = this.mediaUri.toString(),
    artworkUri = this.artworkUri.toString(),
    isFavorite = this.isFavorite,
    folder = this.folder,
    artistId = this.artistId,
    albumId = this.albumId,
    mediaId = this.mediaId
)

fun SongDB.toSong() = Song(
    title = this.title,
    artist = this.artist,
    album = this.album,
    duration = this.duration,
    mediaUri = this.mediaUri.toUri(),
    artworkUri = this.artworkUri.toUri(),
    mediaId = this.mediaId,
    albumId = this.albumId,
    artistId = this.artistId,
    isFavorite = this.isFavorite,
    folder = this.folder,
)