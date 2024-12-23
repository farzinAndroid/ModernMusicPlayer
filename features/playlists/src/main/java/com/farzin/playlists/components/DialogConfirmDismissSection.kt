package com.farzin.playlists.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.theme.WhiteDarkBlue

@Composable
fun DialogConfirmDismissSection(
    onConfirm:()->Unit,
    onDismiss:()->Unit,
    isSongSelected:Boolean,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextButton(
            onClick = onDismiss,
            modifier = Modifier
                .weight(0.5f)
        ) {
            TextMedium(
                text = stringResource(com.farzin.core_ui.R.string.dismiss),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.WhiteDarkBlue,
                maxLine = 1
            )
        }



        TextButton(
            onClick = onConfirm,
            enabled = isSongSelected,
            modifier = Modifier
                .weight(0.5f),
        ) {
            TextMedium(
                text = stringResource(com.farzin.core_ui.R.string.confirm),
                fontSize = 16.sp,
                color = if (isSongSelected) MaterialTheme.colorScheme.WhiteDarkBlue else MaterialTheme.colorScheme.WhiteDarkBlue.copy(0.5f),
                maxLine = 1
            )
        }


    }


}