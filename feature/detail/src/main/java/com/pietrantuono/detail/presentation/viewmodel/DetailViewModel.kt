package com.pietrantuono.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.pietrantuono.common.Logger
import com.pietrantuono.common.RedditViewModel
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.GetPostDetail
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.ImageLoaded
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.Error
import com.pietrantuono.posts.GetPostDetailUseCase
import com.pietrantuono.posts.GetPostDetailUseCase.Params
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCase: GetPostDetailUseCase,
    private val detailMapper: DetailMapper,
    private val handle: SavedStateHandle,
    coroutineContext: CoroutineContext,
    logger: Logger
) : RedditViewModel<DetailUiState, DetailUiEvent>(coroutineContext, logger) {

    override val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState())

    override fun accept(uiEvent: DetailUiEvent) {
        when (uiEvent) {
            is GetPostDetail -> getPostDetail(uiEvent.id)
            is ImageLoaded -> updateState { copy(loading = false) }
            is Error -> TODO()
        }
    }

    private fun getPostDetail(id: String) {
        if (latestState.post != null) return
        launch {
            getPost(id)?.let { updateState { copy(post = it) } }
        }
    }

    private suspend fun getPost(id: String) =
        handle[POST] ?: detailUseCase.execute(Params(id))
            ?.let { detailMapper.map(it) }
            .also { handle[POST] = it }

    private companion object {
        private const val POST = "post"
    }
}
