package com.pietrantuono.posts.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleEventObserver
import com.pietrantuono.home.R
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.GetInitialPosts
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.GetNewPosts
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.OnPostClicked
import com.pietrantuono.posts.presentation.viewmodel.PostsUiState

@Composable
fun PostsScreen(
    postsUiState: PostsUiState = PostsUiState(),
    events: (PostsUiEvent) -> Unit = {}
) {
    Content(postsUiState, events)
    ObserveLifecycle(events)
}

@Composable
private fun ObserveLifecycle(events: (PostsUiEvent) -> Unit) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                ON_RESUME -> events(GetInitialPosts)
                else -> Unit
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content(
    state: PostsUiState,
    events: (PostsUiEvent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = { events(GetNewPosts) }
    )
    Box(
        Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        LazyColumn(Modifier.padding(dimensionResource(R.dimen.small_padding))) {
            items(items = state.posts) {
                RedditCard(
                    modifier = Modifier.clickable { events(OnPostClicked(it.id)) },
                    post = it
                )
            }
        }
        val stringResource = stringResource(id = R.string.loading)
        PullRefreshIndicator(
            refreshing = state.isLoading,
            state = pullRefreshState,
            modifier = Modifier
                .align(TopCenter)
                .semantics {
                    contentDescription = stringResource
                }
        )
    }
}
