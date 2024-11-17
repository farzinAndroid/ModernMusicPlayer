package com.farzin.core_ui.common_components

import android.app.Activity
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.farzin.core_model.Song
import com.farzin.core_ui.utils.showToast
import kotlinx.coroutines.launch


@Composable
fun deleteLauncher(
    songToDelete:Song
) : ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult> {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    scope.launch {
                        context.showToast(context.getString(com.farzin.core_ui.R.string.song_deleted))
                    }
                } else {
                    try {
                        scope.launch {
                            val contentResolver = context.contentResolver
                            val uris = listOf(songToDelete.mediaUri)

                            contentResolver.delete(uris[0], null, null)
                            context.showToast(context.getString(com.farzin.core_ui.R.string.song_deleted))
                        }
                    } catch (e: Throwable) {
                        context.showToast(context.getString(com.farzin.core_ui.R.string.sth_went_wrong))
                        e.printStackTrace()
                    }
                }
            }
        },
    )
    return launcher

}