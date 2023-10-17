package com.pietrantuono.detail.presentation.ui

import android.annotation.SuppressLint
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Success
import com.pietrantuono.common.EMPTY_STRING
import com.pietrantuono.detail.R
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.ErrorConsumed
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.GetPostDetail
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.ImageLoaded
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.OnError
import com.pietrantuono.detail.presentation.viewmodel.DetailUiState
import com.pietrantuono.detail.presentation.viewmodel.ErrorUiModel.Error
import com.pietrantuono.detail.presentation.viewmodel.PostDetailUiModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    postId: String = EMPTY_STRING,
    state: DetailUiState = DetailUiState(),
    events: (DetailUiEvent) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        if (state.loading) {
            Loading()
        }
        PostDetail(
            post = state.post,
            onImageLoaded = { events(ImageLoaded) },
            onError = { message -> events(OnError(message)) }
        )
    }
    LaunchedEffect(postId) {
        events(GetPostDetail(postId))
    }
    LaunchedEffect(state.error) {
        if (state.error is Error) {
            snackbarHostState.showSnackbar(state.error.message)
            events(ErrorConsumed)
        }
    }
}

@Composable
private fun PostDetail(
    post: PostDetailUiModel? = PostDetailUiModel(
        EMPTY_STRING,
        EMPTY_STRING,
        EMPTY_STRING,
        EMPTY_STRING
    ),
    onImageLoaded: () -> Unit = {},
    onError: (String?) -> Unit = {}
) {
    post ?: return
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.small_spacing)),
        verticalArrangement = spacedBy(dimensionResource(R.dimen.small_spacing)),
    ) {
        Text(
            modifier = Modifier.align(End),
            text = stringResource(id = R.string.author, post.author),
            style = MaterialTheme.typography.titleLarge,
        )
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
                    is Success -> onImageLoaded()
                    is AsyncImagePainter.State.Error -> onError(state.result.throwable.message)
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
