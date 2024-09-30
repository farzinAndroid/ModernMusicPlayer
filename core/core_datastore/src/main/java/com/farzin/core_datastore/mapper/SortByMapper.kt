

package com.farzin.core_datastore.mapper

import com.farzin.core_datastore.SortByProto
import com.farzin.core_model.SortBy

internal fun SortBy.asSortByProto() = when (this) {
    SortBy.TITLE -> SortByProto.SORT_BY_TITLE
    SortBy.ARTIST -> SortByProto.SORT_BY_ARTIST
    SortBy.ALBUM -> SortByProto.SORT_BY_ALBUM
    SortBy.DURATION -> SortByProto.SORT_BY_DURATION
    SortBy.DATE_ADDED -> SortByProto.SORT_BY_DATE
}

internal fun SortByProto.asSortBy() = when (this) {
    SortByProto.SORT_BY_TITLE -> SortBy.TITLE
    SortByProto.SORT_BY_ARTIST -> SortBy.ARTIST
    SortByProto.SORT_BY_ALBUM -> SortBy.ALBUM
    SortByProto.SORT_BY_DURATION -> SortBy.DURATION

    SortByProto.UNRECOGNIZED,
    SortByProto.SORT_BY_UNSPECIFIED,
    SortByProto.SORT_BY_DATE -> SortBy.DATE_ADDED
}
