package com.farzin.media_store.utils

import java.io.File


object FilePathUtils{
    internal fun String.asFolder() = File(this).parentFile?.name.orEmpty()
}
