package com.farzin.home.components.folders

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.farzin.core_model.Folder
import com.farzin.core_ui.common_components.EmptySectionText
import com.farzin.core_ui.common_components.FolderItem
import com.farzin.core_ui.theme.spacing

@Composable
fun Folders(
    folders: List<Folder>,
    onFolderClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (folders.isNotEmpty()){
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
        ) {
            itemsIndexed(folders, key = { _, folder->folder.name}){ index, folder ->
                Spacer(Modifier.height(MaterialTheme.spacing.small8))
                FolderItem(
                    folder = folder,
                    onClick = onFolderClick,
                    modifier =Modifier
                        .animateItem()
                )
            }
        }
    }else{
        EmptySectionText(stringResource(com.farzin.core_ui.R.string.no_folder))
    }
}