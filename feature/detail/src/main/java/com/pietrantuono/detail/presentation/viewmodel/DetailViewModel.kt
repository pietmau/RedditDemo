package com.pietrantuono.detail.presentation.viewmodel

import com.pietrantuono.common.Logger
import com.pietrantuono.common.RedditViewModel
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.GetPostDetail
import com.pietrantuono.posts.GetPostDetailUseCase
import com.pietrantuono.posts.GetPostDetailUseCase.Params
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCase: GetPostDetailUseCase,
    private val mapper: DetailMapper,
    coroutineContext: CoroutineContext,
    logger: Logger,
) : RedditViewModel<DetailUiState, DetailUiEvent>(coroutineContext, logger) {

    override val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState())

    override fun accept(uiEvent: DetailUiEvent) {
        when (uiEvent) {
            is GetPostDetail -> getPostDetail(uiEvent.id)
        }
    }

    private fun getPostDetail(id: String?) {
        if (id == null) {
            updateState { copy(loading = false, error = true) }
        } else {
            launch {
                val post = detailUseCase.execute(Params(id))
                post?.let {
                    updateState { copy(loading = false, post = mapper.map(it)) }
                }
            }
        }
    }
}
