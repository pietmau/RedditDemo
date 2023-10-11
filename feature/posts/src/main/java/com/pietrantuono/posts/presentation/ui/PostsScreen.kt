package com.pietrantuono.posts.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pietrantuono.posts.presentation.viewmodel.UiEvent
import com.pietrantuono.posts.presentation.viewmodel.UiEvent.GetPosts
import com.pietrantuono.posts.presentation.viewmodel.UiState

@Composable
fun PostsScreen(uiState: UiState = UiState(), events: (UiEvent) -> Unit = {}) {

    LazyColumn {
        items(items = uiState.posts, // TODO, add error and laoding
            key = { it.id ?: "" }) { post ->
            Text(
                modifier = Modifier.clickable { },
                text = post.title ?: ""
            )
        }
    }
    Spacer(modifier = Modifier.padding(top = 4.dp)) // TODO Externzalizex
    LaunchedEffect(true) {
        events(GetPosts)
    }
}