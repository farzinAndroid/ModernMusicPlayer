package com.farzin.core_ui.common_components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.farzin.core_ui.R
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

@Composable
 fun Long.asFormattedString() = milliseconds.toComponents { minutes, seconds, _ ->
    stringResource(
        id = R.string.player_timestamp_format,
        String.format(locale = Locale.US, format = "%02d", minutes),
        String.format(locale = Locale.US, format = "%02d", seconds)
    )
}

fun convertToProgress(count: Long, total: Long) =
    ((count * ProgressDivider) / total / ProgressDivider).takeIf(Float::isFinite) ?: ZeroProgress

fun convertToPosition(value: Float, total: Long) = (value * total).toLong()

private const val ProgressDivider = 100f
private const val ZeroProgress = 0f