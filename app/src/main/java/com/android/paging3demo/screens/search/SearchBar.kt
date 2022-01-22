package com.android.paging3demo.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import com.android.paging3demo.ui.theme.topAppBarBackgroundColor
import com.android.paging3demo.ui.theme.topAppBarContentColor

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    query: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    navigation: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            value = query,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colors.topAppBarContentColor,
                )
            },
            placeholder = {
                Text(
                    text = "Search",
                    style = TextStyle(
                        color = MaterialTheme.colors.topAppBarContentColor
                    )
                )
            },
            trailingIcon = {
                IconButton(
                    onClick =  {
                        if (query.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            navigation()
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colors.topAppBarContentColor,
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(query)
                    keyboardController?.hide()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.topAppBarContentColor
            ),
        )
    }
}