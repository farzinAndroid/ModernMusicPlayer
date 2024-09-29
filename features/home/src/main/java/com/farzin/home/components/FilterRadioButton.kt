package com.farzin.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farzin.core_ui.common_components.TextRegular
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun FilterRadioButton(
    text: String,
    onClick:()->Unit,
    isSelected:Boolean
) {


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall4),
    ) {

        RadioButton(
            selected = isSelected,
            onClick = onClick,
        )

        TextRegular(
            text = text,
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
            fontSize = 15.sp
        )
    }

}