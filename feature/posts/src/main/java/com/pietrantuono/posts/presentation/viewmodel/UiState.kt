package com.pietrantuono.posts.presentation.viewmodel

data class UiState(val posts: List<PostUiModel> = emptyList())

data class PostUiModel(
    val title: String?,
    val images: List<ImageUiModel> = emptyList(),
    val author: String?,
    val created: Long?,
    val id: String,
)

data class ImageUiModel(
    val url: String? = null,
    val width: Int? = null,
    val height: Int? = null,
)