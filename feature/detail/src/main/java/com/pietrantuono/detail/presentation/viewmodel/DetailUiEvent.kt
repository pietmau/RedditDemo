package com.pietrantuono.detail.presentation.viewmodel

sealed class DetailUiEvent {

    data class GetPostDetail(val id: String) : DetailUiEvent()
}
