package com.example.snapchatclone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    leftContent: @Composable RowScope.() -> Unit = {},
    centerContent: @Composable () -> Unit = {},
    rightContent: @Composable RowScope.() -> Unit = {}
    ) {
    Row(
        modifier = Modifier
            //.fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
       Row(content = leftContent,
           horizontalArrangement = Arrangement.spacedBy(16.dp),
           )
        Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)) {
            centerContent()
        }
       Row(content = rightContent,
           horizontalArrangement = Arrangement.spacedBy(16.dp),
           )
    }
}