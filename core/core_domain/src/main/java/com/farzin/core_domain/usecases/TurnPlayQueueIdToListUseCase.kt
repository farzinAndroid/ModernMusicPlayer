package com.farzin.core_domain.usecases

class TurnPlayQueueIdToListUseCase {
    operator fun invoke(playingQueueIdsString: String): List<String> {
        return if (playingQueueIdsString.isNotEmpty()) {
            playingQueueIdsString.split(",")
        } else {
            emptyList()
        }
    }
}