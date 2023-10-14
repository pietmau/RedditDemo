package com.pietrantuono.redditdemo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pietrantuono.common.EMPTY_STRING
import com.pietrantuono.detail.presentation.ui.DetailScreen
import com.pietrantuono.detail.presentation.viewmodel.DetailUiState
import com.pietrantuono.detail.presentation.viewmodel.DetailViewModel

@Composable
internal fun PostsDetail(id: String = EMPTY_STRING) {
    val viewModel = hiltViewModel<DetailViewModel>()
    val postsUiState by viewModel.uiState.collectAsStateWithLifecycle(DetailUiState())
    DetailScreen(id, postsUiState) {
        viewModel.accept(it)
    }
}
