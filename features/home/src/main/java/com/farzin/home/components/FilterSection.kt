package com.farzin.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.sp
import com.farzin.core_model.SortOrder
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.home.home.HomeViewmodel
import java.util.Locale

@Composable
fun FilterSection(
    showFilter:Boolean,
    sortOrder: SortOrder,
    homeViewmodel: HomeViewmodel
) {

    val sortOrderRadioList = listOf(
        SortOrder.ASCENDING,
        SortOrder.DESCENDING
    )

    val (selectedOption, onOptionSelected) = remember {
        if (sortOrder == SortOrder.ASCENDING)
            mutableStateOf(sortOrderRadioList[0])
        else
            mutableStateOf(sortOrderRadioList[1])
    }

    AnimatedVisibility(
        visible = showFilter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = MaterialTheme.spacing.medium16),
            horizontalAlignment = Alignment.Start
        ) {
            TextMedium(
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
                sortOrderRadioList.forEachIndexed { index, text ->

                    FilterRadioButton(
                        text = text.name.lowercase(Locale.US),
                        isSelected = selectedOption == text,
                        onClick = {
                            onOptionSelected(text)

                            if (text == SortOrder.ASCENDING){
                                homeViewmodel.setSortOrder(SortOrder.ASCENDING)
                            }else{
                                homeViewmodel.setSortOrder(SortOrder.DESCENDING)
                            }
                        }
                    )
                }

            }
        }
    }


}