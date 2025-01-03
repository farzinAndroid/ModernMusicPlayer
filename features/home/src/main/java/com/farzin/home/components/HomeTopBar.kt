package com.farzin.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.farzin.core_ui.common_components.TextBold
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.home.R

@Composable
fun HomeTopBar(
    onSearchClicked:()->Unit,
    onFilterClicked:()->Unit,
    showFilter:Boolean
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(top = MaterialTheme.spacing.large32)
            .padding(horizontal = MaterialTheme.spacing.semiLarge24),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {

        Icon(
            painter = painterResource(com.farzin.core_ui.R.drawable.search) ,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.WhiteDarkBlue,
            modifier = Modifier
                .size(MaterialTheme.spacing.semiLarge24)
                .clickable { onSearchClicked() }

        )


        TextBold(
            text = stringResource(com.farzin.core_ui.R.string.app_name),
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
            fontSize = 22.sp
        )

        if (showFilter){
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.WhiteDarkBlue,
                modifier = Modifier
                    .size(MaterialTheme.spacing.semiLarge24)
                    .clickable { onFilterClicked() }
            )
        }else{
            Icon(
                painter = painterResource(com.farzin.core_ui.R.drawable.filter),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.WhiteDarkBlue,
                modifier = Modifier
                    .size(MaterialTheme.spacing.semiLarge24)
                    .clickable { onFilterClicked() }
            )
        }
    }


}