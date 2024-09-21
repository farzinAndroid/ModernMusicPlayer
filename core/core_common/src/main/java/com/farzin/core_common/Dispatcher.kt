package com.farzin.core_common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val musicDispatchers: MusicDispatchers)

enum class MusicDispatchers { DEFAULT, MAIN, UNCONFINED, IO }