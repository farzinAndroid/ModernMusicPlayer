

package com.farzin.core_datastore.mapper

import com.farzin.core_datastore.SortOrderProto
import com.farzin.core_model.SortOrder

internal fun SortOrder.asSortOrderProto() = when (this) {
    SortOrder.ASCENDING -> SortOrderProto.SORT_ORDER_ASCENDING
    SortOrder.DESCENDING -> SortOrderProto.SORT_ORDER_DESCENDING
}

internal fun SortOrderProto.asSortOrder() = when (this) {
    SortOrderProto.SORT_ORDER_ASCENDING -> SortOrder.ASCENDING

    SortOrderProto.UNRECOGNIZED,
    SortOrderProto.SORT_ORDER_UNSPECIFIED,
    SortOrderProto.SORT_ORDER_DESCENDING -> SortOrder.DESCENDING
}
