package com.farzin.core_ui.common_components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.farzin.core_ui.R

@Composable
fun TextBold(
    modifier: Modifier = Modifier,
    text:String,
    color: Color,
    fontSize:TextUnit,
    maxLine:Int = Int.MAX_VALUE,
    lineHeight:TextUnit = TextUnit.Unspecified,
    textStyle: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip
) {


    Text(
        text =text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        maxLines = maxLine,
        lineHeight = lineHeight,
        fontFamily = FontFamily(Font(R.font.gilroy_bold)),
        style = textStyle,
        overflow = overflow
    )
}