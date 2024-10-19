package com.farzin.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farzin.core_ui.R
import com.farzin.core_ui.common_components.TextRegular
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.WhiteDarkBlue


@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClearClicked:()->Unit
) {


    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(60.dp),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            TextRegular(
                text = stringResource(com.farzin.core_ui.R.string.search),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.Gray
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier.padding(end = 8.dp),
                tint = MaterialTheme.colorScheme.BackgroundColor
            )
        },
        trailingIcon = {
            if (value.isNotEmpty() || value.isNotBlank()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            onClearClicked()
                        },
                    tint = MaterialTheme.colorScheme.BackgroundColor
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.WhiteDarkBlue,
            focusedContainerColor = MaterialTheme.colorScheme.WhiteDarkBlue,
            focusedTextColor = MaterialTheme.colorScheme.BackgroundColor,
            unfocusedTextColor = MaterialTheme.colorScheme.BackgroundColor,
            cursorColor = MaterialTheme.colorScheme.BackgroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        maxLines = 1,
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_medium)),
            fontWeight = FontWeight.ExtraLight,
            color = MaterialTheme.colorScheme.BackgroundColor
        ),
        shape = Shapes().extraLarge
    )

}