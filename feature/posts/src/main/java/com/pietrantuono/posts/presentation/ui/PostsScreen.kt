package com.pietrantuono.posts.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.pietrantuono.home.R
import com.pietrantuono.posts.presentation.viewmodel.PostUiModel
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.GetPosts
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.OnPostClicked
import com.pietrantuono.posts.presentation.viewmodel.PostsUiState

@Composable
fun PostsScreen(
    postsUiState: PostsUiState = PostsUiState(),
    events: (PostsUiEvent) -> Unit = {}
) {
    when (postsUiState.isLoading) {
        true -> Loading()
        false -> Content(postsUiState.posts, events)
    }
    LaunchedEffect(true) {
        events(GetPosts)
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(dimensionResource(R.dimen.default_size)),
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Composable
private fun Content(
    posts: List<PostUiModel>,
    events: (PostsUiEvent) -> Unit
) {
    LazyColumn(Modifier.padding(dimensionResource(R.dimen.small_padding))) {
        items(items = posts) {
            RedditCard(
                modifier = Modifier.clickable { events(OnPostClicked(it.id)) },
                post = it
            )
        }
    }
}
