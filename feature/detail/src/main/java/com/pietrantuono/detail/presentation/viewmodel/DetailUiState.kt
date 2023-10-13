package com.pietrantuono.detail.presentation.viewmodel

data class DetailUiState(val loading: Boolean = true, val post: PostDetailUiModel? = null)

data class PostDetailUiModel(
    val id: String,
    val title: String? = null,
    val author: String? = null,
    val created: Long? = null,
    val thumbnail: String? = null,
    val images: List<ImageUiModel> = emptyList(),
)

data class ImageUiModel(
    val url: String? = null,
    val width: Int? = null,
    val height: Int? = null,
)

sealed class DetailUiEvent {
    data class GetPostDetail(val id: String) : DetailUiEvent()
}