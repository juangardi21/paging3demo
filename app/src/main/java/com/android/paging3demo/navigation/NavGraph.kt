package com.android.paging3demo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.android.paging3demo.screens.home.HomeScreen
import com.android.paging3demo.screens.search.SearchScreen

@ExperimentalComposeUiApi
@ExperimentalPagingApi
@ExperimentalCoilApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navigation = {
                navController.navigate(Screen.Search.route)
            })
        }

        composable(route = Screen.Search.route) {
            SearchScreen(navigation = {
                navController.navigate(Screen.Home.route)
            })
        }
    }

}