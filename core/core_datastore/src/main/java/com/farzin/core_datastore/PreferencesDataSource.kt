

package com.farzin.core_datastore

import androidx.datastore.core.DataStore
import com.farzin.core_datastore.mapper.asPlaybackMode
import com.farzin.core_datastore.mapper.asPlaybackModeProto
import com.farzin.core_datastore.mapper.asSortBy
import com.farzin.core_datastore.mapper.asSortByProto
import com.farzin.core_datastore.mapper.asSortOrder
import com.farzin.core_datastore.mapper.asSortOrderProto
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder
import com.farzin.core_model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData = userPreferences.data.map { preferences ->
        UserData(
            playingQueueIds = preferences.playingQueueIdsList,
            playingQueueIndex = preferences.playingQueueIndex,
            playbackMode = preferences.playbackMode.asPlaybackMode(),
            sortOrder = preferences.sortOrder.asSortOrder(),
            sortBy = preferences.sortBy.asSortBy(),
//            favoriteSongs = preferences.favoriteSongIdsMap.keys,
        )
    }

    suspend fun setPlayingQueueIds(playingQueueIds: List<String>) {
        userPreferences.updateData {
            it.copy {
                this.playingQueueIds.run {
                    clear()
                    addAll(playingQueueIds)
                }
            }
        }
    }

    suspend fun setPlayingQueueIndex(playingQueueIndex: Int) {
        userPreferences.updateData {
            it.copy {
                this.playingQueueIndex = playingQueueIndex
            }
        }
    }

    suspend fun setPlaybackMode(playbackMode: PlaybackMode) {
        userPreferences.updateData {
            it.copy {
                this.playbackMode = playbackMode.asPlaybackModeProto()
            }
        }
    }

    suspend fun setSortOrder(sortOrder: SortOrder) {
        userPreferences.updateData {
            it.copy {
                this.sortOrder = sortOrder.asSortOrderProto()
            }
        }
    }

    suspend fun setSortBy(sortBy: SortBy) {
        userPreferences.updateData {
            it.copy {
                this.sortBy = sortBy.asSortByProto()
            }
        }
    }

    suspend fun toggleFavoriteSong(id: String, isFavorite: Boolean) {
        userPreferences.updateData {
            it.copy {
                if (isFavorite) {
                    favoriteSongIds.put(id, true)
                } else {
                    favoriteSongIds.remove(id)
                }
            }
        }
    }
}
