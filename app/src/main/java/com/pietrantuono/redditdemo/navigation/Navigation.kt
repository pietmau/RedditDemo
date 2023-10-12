package com.pietrantuono.redditdemo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination

internal const val POSTS = "posts"
internal const val ID = "id"
internal const val DETAIL = "detail"


@Composable
internal fun RedditNavHost(startDestination: String, block: NavGraphBuilder.(NavHostController) -> Unit) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        block(navController)
    }
}

fun NavHostController.navigateTo(navDestination: NavigationDestination) {
    when (navDestination) {
        is NavigationDestination.PostDetails -> navigate("$DETAIL/${navDestination.postId}")
        else -> Unit
    }
}
