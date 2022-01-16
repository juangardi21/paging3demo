package com.android.paging3demo.screens.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.paging3demo.ui.theme.topAppBarBackgroundColor
import com.android.paging3demo.ui.theme.topAppBarContentColor

@Composable
fun TopBar(
    title: String,
    onSearchClicked: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            IconButton(
                onClick = onSearchClicked
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colors.topAppBarContentColor,
                    contentDescription = "Search icon",
                )
            }
        }
    )
}

@Preview
@Composable
fun Preview() {
    TopBar(title = "Home", onSearchClicked = {})
}