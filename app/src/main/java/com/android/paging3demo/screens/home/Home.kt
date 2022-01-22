package com.android.paging3demo.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.android.paging3demo.navigation.Screen
import com.android.paging3demo.screens.common.ListContent

@ExperimentalPagingApi
@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navigation: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val getAllImages = homeViewModel.getAllImages.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopBar(
                title = Screen.Home.name,
                onSearchClicked = navigation
            )
        }
    )
    {
        ListContent(
            items = getAllImages,
        )
    }
}