package com.farzin.core_domain.usecases

data class PreferencesUseCases(
    val getUserDataUseCase: GetUserDataUseCase,
    val setPlaybackModeUseCase: SetPlaybackModeUseCase,
    val setSortOrderUseCase: SetSortOrderUseCase,
    val setSortByUseCase: SetSortByUseCase,
    val setPlayingQueueIndexUseCase: SetPlayingQueueIndexUseCase,
    val setPlayingQueueIdsUseCase: SetPlayingQueueIdsUseCase,
)