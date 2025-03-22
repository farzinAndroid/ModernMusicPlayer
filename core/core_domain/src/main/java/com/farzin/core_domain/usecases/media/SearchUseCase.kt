package com.farzin.core_domain.usecases.media

import com.farzin.core_domain.repository.media.MediaRepository
import com.farzin.core_model.SearchDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(query: String) = combine(
        mediaRepository.songs,
        mediaRepository.albums,
        mediaRepository.artists,
        mediaRepository.folders
    ) { songs, albums, artists, folders ->
        SearchDetails(
            folders = folders,
            artists = artists,
            songs = songs,
            albums = albums
        )
    }
        .mapLatest { searchDetail ->
            if (query.isBlank()) {
                return@mapLatest searchDetail.copy(
                    artists = emptyList(),
                    songs = emptyList(),
                    albums = emptyList(),
                    folders = emptyList(),
                )
            }

            val songs = searchDetail.songs.filter {
                it.title.contains(query, ignoreCase = true)
//                it.artist.contains(query, ignoreCase = true)
//                it.album.contains(query, ignoreCase = true)
            }

            val albums = searchDetail.albums.filter {
                it.name.contains(query, ignoreCase = true)
//                it.artist.contains(query, ignoreCase = true)
            }

            val artists = searchDetail.artists.filter {
                it.name.contains(query, ignoreCase = true)
            }

            val folders = searchDetail.folders.filter {
                it.name.contains(query, ignoreCase = true)
            }

            return@mapLatest searchDetail.copy(
                songs, artists, albums, folders
            )
        }
}