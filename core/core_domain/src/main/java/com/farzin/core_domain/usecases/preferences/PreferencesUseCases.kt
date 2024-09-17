package com.farzin.core_domain.usecases.preferences

data class PreferencesUseCases(
    val getUserDataUseCase: GetUserDataUseCase,
    val setPlaybackModeUseCase: SetPlaybackModeUseCase,
    val setSortOrderUseCase: SetSortOrderUseCase,
    val setSortByUseCase: SetSortByUseCase,
    val setPlayingQueueIndexUseCase: SetPlayingQueueIndexUseCase,
    val setPlayingQueueIdsUseCase: SetPlayingQueueIdsUseCase,
)