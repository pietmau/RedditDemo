package com.pietrantuono.detail.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.pietrantuono.common.EMPTY_STRING
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent
import com.pietrantuono.detail.presentation.viewmodel.DetailUiState
import com.pietrantuono.detail.presentation.viewmodel.PostDetailUiModel

@Composable
fun DetailScreen(postId: String? = null, postsUiState: DetailUiState = DetailUiState(), events: (DetailUiEvent) -> Unit = { }) {
    if (postsUiState.post != null) {
        PostDetail(postsUiState.post)
    }
    LaunchedEffect(postId) {
        events(DetailUiEvent.GetPostDetail(postId))
    }
}

@Composable
private fun PostDetail(post: PostDetailUiModel) {
    post.image?.let {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = it,
            contentDescription = post.title ?: EMPTY_STRING
        )
    }
}
