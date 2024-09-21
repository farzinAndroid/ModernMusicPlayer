

package com.farzin.core_media_service.util

fun <T> unsafeLazy(initializer: () -> T) =
    lazy(mode = LazyThreadSafetyMode.NONE, initializer = initializer)
