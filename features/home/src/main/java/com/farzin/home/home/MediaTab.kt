package com.farzin.home.home

import androidx.annotation.StringRes

internal enum class MediaTab(@StringRes val titleResource: Int) {
    Songs(titleResource = com.farzin.core_ui.R.string.songs),
    Artists(titleResource = com.farzin.core_ui.R.string.artists),
    Albums(titleResource = com.farzin.core_ui.R.string.albums),
    Folders(titleResource = com.farzin.core_ui.R.string.folders)
}