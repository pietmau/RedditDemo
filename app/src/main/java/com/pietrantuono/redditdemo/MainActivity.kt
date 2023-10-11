package com.pietrantuono.redditdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pietrantuono.posts.presentation.ui.PostsScreen
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination.PostDetails
import com.pietrantuono.posts.presentation.viewmodel.PostsViewModel
import com.pietrantuono.posts.presentation.viewmodel.UiEvent.NavigationPerformed
import com.pietrantuono.posts.presentation.viewmodel.UiState
import com.pietrantuono.redditdemo.navigation.DETAIL
import com.pietrantuono.redditdemo.navigation.ID
import com.pietrantuono.redditdemo.navigation.POSTS
import com.pietrantuono.redditdemo.navigation.detailNavArguments
import com.pietrantuono.redditdemo.ui.theme.RedditDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RedditDemoTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = POSTS
                ) {
                    composable(route = POSTS) {
                        val viewModel = hiltViewModel<PostsViewModel>()
                        val uiState by viewModel.uiState.collectAsStateWithLifecycle(UiState())
                        PostsScreen(uiState) { event ->
                            viewModel.accept(event)
                        }
                        LaunchedEffect(uiState.navDestination) {
                            navController.navigateTo(uiState.navDestination)
                            viewModel.accept(NavigationPerformed)
                        }
                    }
                    composable(
                        route = "$DETAIL/{$ID}",
                        arguments = detailNavArguments
                    ) {}
                }
            }
        }
    }
}

private fun NavHostController.navigateTo(navDestination: NavigationDestination) {
    when (navDestination) {
        is PostDetails -> navigate("$DETAIL/${navDestination.postId}")
        else -> Unit
    }
}
