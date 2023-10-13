package com.pietrantuono.redditdemo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pietrantuono.posts.presentation.ui.PostsScreen
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent
import com.pietrantuono.posts.presentation.viewmodel.PostsUiState
import com.pietrantuono.posts.presentation.viewmodel.PostsViewModel

@Composable
internal fun AllPosts(navigation: (NavigationDestination) -> Unit) {
    val viewModel = hiltViewModel<PostsViewModel>()
    val postsUiState by viewModel.uiState.collectAsStateWithLifecycle(PostsUiState())
    PostsScreen(postsUiState) {
        viewModel.accept(it)
    }
    LaunchedEffect(postsUiState.navDestination) {
        navigation(postsUiState.navDestination)
        viewModel.accept(PostsUiEvent.NavigationPerformed)
    }
}
