package com.pietrantuono.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import javax.inject.Inject

class DetailScreenState @Inject constructor(private val savedStateHandle: SavedStateHandle) {
    var post: PostDetailUiModel?
        get() = savedStateHandle[POST]
        set(value) {
            savedStateHandle[POST] = value
        }
    var errorConsumed: Boolean
        get() = savedStateHandle[ERROR_CONSUMED] ?: false
        set(value) {
            savedStateHandle[ERROR_CONSUMED] = value
        }

    private companion object {
        private const val POST = "post"
        private const val ERROR_CONSUMED = "error_consumed"
    }
}
