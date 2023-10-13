package com.pietrantuono.posts.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.pietrantuono.home.R
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.GetPosts
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.OnPostClicked
import com.pietrantuono.posts.presentation.viewmodel.PostsUiState

@Composable
fun PostsScreen(
    postsUiState: PostsUiState = PostsUiState(),
    events: (PostsUiEvent) -> Unit = {},
) {
    LazyColumn {
        items(
            items = postsUiState.posts
        ) { post ->
            RedditCard(
                modifier = Modifier.clickable { events(OnPostClicked(post.id)) },
                post = post
            )
        }
    }
    Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.small_padding)))
    LaunchedEffect(true) {
        events(GetPosts)
    }
}
