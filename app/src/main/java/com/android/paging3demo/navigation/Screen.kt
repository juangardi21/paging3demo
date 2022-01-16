package com.android.paging3demo.navigation

sealed class Screen(
    val route: String,
    val name: String,
) {
    object Home: Screen(
        route = "home_screen",
        name = "Home"
    )
    object Search: Screen(
        route = "search_screen",
        name = "Search"
    )
}