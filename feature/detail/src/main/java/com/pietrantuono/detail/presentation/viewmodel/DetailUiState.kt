package com.pietrantuono.detail.presentation.viewmodel

import android.os.Parcelable
import com.pietrantuono.detail.presentation.viewmodel.ErrorUiModel.None
import kotlinx.parcelize.Parcelize

data class DetailUiState(
    val loading: Boolean = true,
    val post: PostDetailUiModel? = null,
    val error: ErrorUiModel = None
)

sealed class ErrorUiModel {
    object None : ErrorUiModel()

    data class Error(val message: String) : ErrorUiModel()

    object Consumed : ErrorUiModel()

}

@Parcelize
data class PostDetailUiModel(
    val id: String,
    val title: String,
    val text: String,
    val author: String,
    val created: Long? = null,
    val image: String? = null
) : Parcelable
