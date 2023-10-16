package com.pietrantuono.redditdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pietrantuono.redditdemo.navigation.DETAIL
import com.pietrantuono.redditdemo.navigation.ID
import com.pietrantuono.redditdemo.navigation.POSTS
import com.pietrantuono.redditdemo.navigation.RedditNavHost
import com.pietrantuono.redditdemo.navigation.navigateTo
import com.pietrantuono.redditdemo.ui.AllPosts
import com.pietrantuono.redditdemo.ui.PostsDetail
import com.pietrantuono.redditdemo.ui.theme.RedditDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RedditDemoTheme {
                RedditNavHost { controller ->
                    composable(route = POSTS) {
                        AllPosts { destination ->
                            controller.navigateTo(destination)
                        }
                    }
                    composable(
                        route = "$DETAIL/{$ID}",
                        arguments = listOf(navArgument(ID) { type = StringType })
                    ) { backStackEntry ->
                        val postId = backStackEntry.arguments?.getString(ID) ?: return@composable
                        PostsDetail(postId)
                    }
                }
            }
        }
    }
}
