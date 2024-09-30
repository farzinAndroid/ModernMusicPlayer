package com.farzin.core_domain.usecases.media

import com.farzin.core_domain.repository.MediaRepository
import com.farzin.core_domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPlayingQueueSongsUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    operator fun invoke() = combine(
        mediaRepository.songs,
        sharedPreferencesRepository.userData.map { it.playingQueueIds }
    ) { songs, playingQueueIds ->
        playingQueueIds.mapNotNull { playingQueueId -> songs.find { it.mediaId == playingQueueId } }
    }
}