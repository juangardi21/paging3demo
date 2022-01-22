package com.android.paging3demo.screens.search

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.android.paging3demo.screens.common.ListContent

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalPagingApi
@Composable
fun SearchScreen(
    navigation: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val searchedImages = viewModel.searchedImages.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchBar(
                query = viewModel.query,
                onTextChange = {
                    viewModel.query = it
                },
                onSearchClicked = {
                    viewModel.searchImages()
                },
                navigation = navigation,
            )
        }
    )
    {
        ListContent(
            items = searchedImages,
        )
    }
}