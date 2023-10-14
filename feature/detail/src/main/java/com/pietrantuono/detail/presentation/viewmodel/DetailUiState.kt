package com.pietrantuono.detail.presentation.viewmodel

data class DetailUiState(
    val loading: Boolean = true,
    val post: PostDetailUiModel? = null,
    val error: Boolean = false
)

data class PostDetailUiModel(
    val id: String,
    val title: String,
    val text: String,
    val author: String? = null,
    val created: Long? = null,
    val image: String? = null
)
