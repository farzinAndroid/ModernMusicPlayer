package com.farzin.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.farzin.core_ui.common_components.TextBold
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.DarkGray
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun HeaderText(
    headerText: String,
) {
    Column(
        modifier = Modifier
            .padding(top = MaterialTheme.spacing.small8)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.DarkGray.copy(0.5f))
    ) {
        TextBold(
            text = headerText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = MaterialTheme.spacing.small8)
                .padding(vertical = MaterialTheme.spacing.extraSmall4),
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
            fontSize = 18.sp,
            textStyle = TextStyle(
                textAlign = TextAlign.Start
            )
        )
    }
}