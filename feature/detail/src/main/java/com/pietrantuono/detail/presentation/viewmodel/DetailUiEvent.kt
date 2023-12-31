package com.pietrantuono.detail.presentation.viewmodel

sealed class DetailUiEvent {

    data class GetPostDetail(val id: String) : DetailUiEvent()

    object ImageLoaded : DetailUiEvent()

    data class OnError(val message: String? = null) : DetailUiEvent()

    object ErrorConsumed : DetailUiEvent()
}
