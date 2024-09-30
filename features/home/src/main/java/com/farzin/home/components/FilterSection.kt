package com.farzin.home.components

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder
import com.farzin.core_ui.common_components.TextBold
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSection(
    showFilter:Boolean,
    sortOrder: SortOrder,
    sortBy: SortBy,
    onSortOrderClicked:(SortOrder)->Unit,
    onSortByClicked:(SortBy)->Unit,
) {

    val context = LocalContext.current

    /**
     * SortOrder
     */
    val sortOrderRadioList = listOf(
        SortOrder.ASCENDING,
        SortOrder.DESCENDING
    )
    val (sortOrderSelectedOption, onSortOrderOptionSelected) = remember {
        if (sortOrder == SortOrder.ASCENDING)
            mutableStateOf(sortOrderRadioList[0])
        else
            mutableStateOf(sortOrderRadioList[1])
    }


    /**
     * SortBy
     */
    val sortByRadioList = listOf(
        SortBy.TITLE,
        SortBy.ARTIST,
        SortBy.ALBUM,
        SortBy.DURATION,
        SortBy.DATE_ADDED,
    )
    val (sortBySelectedOption, onSortByOptionSelected) = remember {
       when(sortBy){
           SortBy.TITLE -> mutableStateOf(sortByRadioList[0])
           SortBy.ARTIST -> mutableStateOf(sortByRadioList[1])
           SortBy.ALBUM -> mutableStateOf(sortByRadioList[2])
           SortBy.DURATION -> mutableStateOf(sortByRadioList[3])
           SortBy.DATE_ADDED -> mutableStateOf(sortByRadioList[4])
       }
    }


    AnimatedVisibility(
        visible = showFilter,
        enter = expandIn(expandFrom = Alignment.BottomCenter) + fadeIn(),
        exit = shrinkOut(shrinkTowards = Alignment.BottomCenter) + fadeOut(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = MaterialTheme.spacing.semiLarge24),
            horizontalAlignment = Alignment.Start
        ) {
            TextBold(
                text = stringResource(com.farzin.core_ui.R.string.sort_order),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.WhiteDarkBlue,
                modifier = Modifier
                    .padding(start = MaterialTheme.spacing.medium16)
            )

            Spacer(Modifier.height(MaterialTheme.spacing.medium16))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium16),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                sortOrderRadioList.forEachIndexed { index, sortOrder ->

                    FilterRadioButton(
                        text = getSortOrderText(sortOrder,context),
                        isSelected = sortOrderSelectedOption == sortOrder,
                        onClick = {
                            onSortOrderOptionSelected(sortOrder)
                            onSortOrderClicked(sortOrder)
                        }
                    )
                }

            }

            TextBold(
                text = stringResource(com.farzin.core_ui.R.string.sort_by),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.WhiteDarkBlue,
                modifier = Modifier
                    .padding(start = MaterialTheme.spacing.medium16)
                    .padding(top = MaterialTheme.spacing.medium16)
            )

            Spacer(Modifier.height(MaterialTheme.spacing.medium16))

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium16),
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.Center
            ) {
                sortByRadioList.forEachIndexed { index, sortBy ->
                    Row {
                        FilterRadioButton(
                            text = getSortByText(sortBy,context),
                            isSelected = sortBySelectedOption == sortBy,
                            onClick = {
                                onSortByOptionSelected(sortBy)
                                onSortByClicked(sortBy)
                            }
                        )
                        Spacer(Modifier.width(MaterialTheme.spacing.small8))
                    }
                }
            }
        }


    }


}

private fun getSortOrderText(sortOrder: SortOrder,context: Context) : String{
    return when(sortOrder){
        SortOrder.ASCENDING -> context.getString(com.farzin.core_ui.R.string.ascending)
        SortOrder.DESCENDING -> context.getString(com.farzin.core_ui.R.string.descending)
    }
}

private fun getSortByText(sortBy: SortBy, context: Context) : String{
    return when(sortBy){
        SortBy.TITLE -> context.getString(com.farzin.core_ui.R.string.title)
        SortBy.ARTIST -> context.getString(com.farzin.core_ui.R.string.artist)
        SortBy.ALBUM -> context.getString(com.farzin.core_ui.R.string.album)
        SortBy.DURATION -> context.getString(com.farzin.core_ui.R.string.duration)
        SortBy.DATE_ADDED -> context.getString(com.farzin.core_ui.R.string.date_added)
    }
}