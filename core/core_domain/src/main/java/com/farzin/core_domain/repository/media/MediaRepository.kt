package com.farzin.core_domain.repository.media

import com.farzin.core_model.Album
import com.farzin.core_model.Artist
import com.farzin.core_model.Folder
import com.farzin.core_model.Song
import kotlinx.coroutines.flow.Flow


interface MediaRepository {
    val songs: Flow<List<Song>>
    val recentlyAdded: Flow<List<Song>>
    val artists: Flow<List<Artist>>
    val albums: Flow<List<Album>>
    val folders: Flow<List<Folder>>
}