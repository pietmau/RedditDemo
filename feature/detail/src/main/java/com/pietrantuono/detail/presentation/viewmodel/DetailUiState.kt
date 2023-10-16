package com.pietrantuono.detail.presentation.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class DetailUiState(
    val loading: Boolean = true,
    val post: PostDetailUiModel? = null,
    val error: Boolean = false
)

@Parcelize
data class PostDetailUiModel(
    val id: String,
    val title: String,
    val text: String,
    val author: String,
    val created: Long? = null,
    val image: String? = null
) : Parcelable
