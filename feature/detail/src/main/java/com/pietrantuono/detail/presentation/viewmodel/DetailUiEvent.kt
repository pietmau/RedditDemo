package com.pietrantuono.detail.presentation.viewmodel

sealed class DetailUiEvent {

    data class GetPostDetail(val id: String) : DetailUiEvent()

    object ImageLoaded : DetailUiEvent()

    data class Error(val message: String? = null) : DetailUiEvent()
}
