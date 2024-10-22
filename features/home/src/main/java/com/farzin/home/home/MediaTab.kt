package com.farzin.home.home

import androidx.annotation.StringRes

internal enum class MediaTab(@StringRes val titleResource: Int) {
    Songs(titleResource = com.farzin.core_ui.R.string.songs),
    Albums(titleResource = com.farzin.core_ui.R.string.albums),
    Artists(titleResource = com.farzin.core_ui.R.string.artists),
    Folders(titleResource = com.farzin.core_ui.R.string.folders),
    Favorites(titleResource = com.farzin.core_ui.R.string.favorites),
    RecentlyAdded(titleResource = com.farzin.core_ui.R.string.recently_added),
}