package com.pietrantuono.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import javax.inject.Inject

class DetailScreenState @Inject constructor(private val savedStateHandle: SavedStateHandle) {
    var redditPost: PostDetailUiModel?
        get() = savedStateHandle[POST]
        set(value) {
            savedStateHandle[POST] = value
        }

    private companion object {
        private const val POST = "post"
    }
}
