package com.pietrantuono.detail.presentation.ui

import androidx.compose.runtime.Composable
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent
import com.pietrantuono.detail.presentation.viewmodel.DetailUiState

@Composable
fun DetailScreen(string: String? = null, postsUiState: DetailUiState = DetailUiState(), events: (DetailUiEvent) -> Unit = { }) {
}
