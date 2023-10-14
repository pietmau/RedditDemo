package com.pietrantuono.detail.presentation.ui

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Error
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
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
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
    post: PostDetailUiModel? = PostDetailUiModel(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING),
    onImageLoaded: () -> Unit = {}
) {
    post ?: return
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.small_spacing)),
        verticalArrangement = spacedBy(dimensionResource(R.dimen.small_spacing)),
    ) {
        Text(
            text = post.title,
            style = MaterialTheme.typography.displayLarge,
        )
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = post.image,
            contentDescription = post.title,
            onState = { state ->
                when (state) {
                    is Success, is Error -> onImageLoaded()
                    else -> Unit
                }
            }
        )
        Text(
            text = post.text,
            style = MaterialTheme.typography.displaySmall,
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
