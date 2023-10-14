package com.pietrantuono.detail.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Success
import com.pietrantuono.common.EMPTY_STRING
import com.pietrantuono.detail.R
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.GetPostDetail
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.ImageLoaded
import com.pietrantuono.detail.presentation.viewmodel.DetailUiState
import com.pietrantuono.detail.presentation.viewmodel.PostDetailUiModel

@Composable
fun DetailScreen(
    postId: String = EMPTY_STRING,
    state: DetailUiState = DetailUiState(),
    events: (DetailUiEvent) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.loading) {
            Loading()
        }
        PostDetail(state.post) {
            events(ImageLoaded)
        }
    }
    LaunchedEffect(postId) {
        events(GetPostDetail(postId))
    }
}

@Composable
private fun PostDetail(
    post: PostDetailUiModel? = PostDetailUiModel(EMPTY_STRING),
    onImageLoaded: () -> Unit = {}
) {
    post?.image?.let {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = it,
            contentDescription = post.title ?: EMPTY_STRING,
            onState = { state ->
                when (state) {
                    is Success -> onImageLoaded()
                    else -> Unit
                }
            }
        )
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
