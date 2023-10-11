package com.pietrantuono.redditdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pietrantuono.posts.presentation.ui.PostsScreen
import com.pietrantuono.posts.presentation.viewmodel.PostsViewModel
import com.pietrantuono.posts.presentation.viewmodel.UiState
import com.pietrantuono.redditdemo.ui.theme.RedditDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RedditDemoTheme {
                val viewModel = hiltViewModel<PostsViewModel>()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle(UiState())
                PostsScreen(uiState) { event ->
                    viewModel.accept(event)
                }
            }
        }
    }
}
