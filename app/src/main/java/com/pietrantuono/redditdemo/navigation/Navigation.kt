package com.pietrantuono.redditdemo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination.PostDetails

internal const val POSTS = "posts"
internal const val ID = "id"
internal const val DETAIL = "detail"


@Composable
internal fun RedditNavHost(block: NavGraphBuilder.(NavHostController) -> Unit) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = POSTS
    ) {
        block(navController)
    }
}

fun NavHostController.navigateTo(navDestination: NavigationDestination) {
    when (navDestination) {
        is PostDetails -> navigate("$DETAIL/${navDestination.postId}")
        else -> Unit
    }
}
