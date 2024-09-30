package com.farzin.media_store.source

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import com.farzin.core_model.Song
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder
import com.farzin.media_store.utils.ArtWorkUtils.toArtworkUri
import com.farzin.media_store.utils.CursorUtils.getLong
import com.farzin.media_store.utils.CursorUtils.getString
import com.farzin.media_store.utils.DateTimeUtils.toLocalDateTime
import com.farzin.media_store.utils.FilePathUtils.asFolder
import com.farzin.media_store.utils.MediaStoreUtils
import com.farzin.media_store.utils.MediaStoreUtils.observe
import com.farzin.media_store.utils.SortUtils
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaStoreSource @Inject constructor(
    private val contentResolver: ContentResolver
) {


    fun getSongs(
        sortBy: SortBy,
        sortOrder: SortOrder
    ) = contentResolver.observe(MediaStoreUtils.Collection).map {
        buildList {
            contentResolver.query(
                /* uri = */ MediaStoreUtils.Collection,
                /* projection = */ MediaStoreUtils.Projection,
                /* selection = */ "${MediaStore.Audio.Media.IS_MUSIC} = 1",
                /* selectionArgs = */ null,
                /* sortOrder = */ SortUtils.getSortOrder(sortOrder, sortBy)
            )?.use {cursor->
                while (cursor.moveToNext()){
                    val id = cursor.getLong(MediaStore.Audio.Media._ID)
                    val title = cursor.getString(MediaStore.Audio.Media.TITLE)
                    val albumId = cursor.getLong(MediaStore.Audio.Media.ALBUM_ID)
                    val album = cursor.getString(MediaStore.Audio.Media.ALBUM)
                    val artistId = cursor.getLong(MediaStore.Audio.Media.ARTIST_ID)
                    val artist = cursor.getString(MediaStore.Audio.Media.ARTIST)
                    val duration = cursor.getLong(MediaStore.Audio.Media.DURATION)
                    val date = cursor.getLong(MediaStore.Audio.Media.DATE_ADDED)
                    val artworkUri = albumId.toArtworkUri()
                    val folder = cursor.getString(MediaStore.Audio.Media.DATA).asFolder()

                    val mediaUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    Song(
                        mediaId = id.toString(),
                        title = title,
                        albumId = albumId,
                        album = album,
                        artistId = artistId,
                        artist = artist,
                        duration = duration,
                        date = date.toLocalDateTime(),
                        mediaUri = mediaUri,
                        artworkUri = artworkUri,
                        folder = folder
                    ).let(::add)
                }
            }
        }
    }


}