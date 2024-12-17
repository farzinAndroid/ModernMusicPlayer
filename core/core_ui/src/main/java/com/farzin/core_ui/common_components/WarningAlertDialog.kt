package com.farzin.core_ui.common_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.farzin.core_ui.R
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarningAlertDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {


    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            decorFitsSystemWindows = true,
        )
    ) {
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = MaterialTheme.colorScheme.BackgroundColor
        ) {
            Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium16)) {
                Spacer(
                    modifier =
                    Modifier.height(MaterialTheme.spacing.medium16)
                )

                TextMedium(
                    text = stringResource(R.string.alert_dialog_warning),
                    color = MaterialTheme.colorScheme.WhiteDarkBlue,
                    fontSize = 16.sp,
                    maxLine = 2
                )


                Spacer(Modifier.height(MaterialTheme.spacing.large32))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {

                    TextButton(
                        onClick = {
                            onDismiss()
                        },
                    ) {
                        TextRegular(
                            text = stringResource(R.string.no),
                            color = MaterialTheme.colorScheme.WhiteDarkBlue,
                            fontSize = 14.sp,
                            maxLine = 1
                        )
                    }

                    TextButton(
                        onClick = {
                            onConfirm()
                        },
                    ) {
                        TextRegular(
                            text = stringResource(R.string.yes),
                            color = MaterialTheme.colorScheme.WhiteDarkBlue,
                            fontSize = 14.sp,
                            maxLine = 1
                        )
                    }


                }
            }
        }

    }
}
