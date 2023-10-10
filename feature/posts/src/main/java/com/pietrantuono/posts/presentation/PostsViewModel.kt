package com.pietrantuono.posts.presentation

import com.pietrantuono.common.Logger
import com.pietrantuono.common.RedditViewModel
import com.pietrantuono.posts.domain.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val useCase: GetPostsUseCase,
    coroutineContext: CoroutineContext,
    logger: Logger,
) : RedditViewModel<UiState, UiEvent>(coroutineContext, logger) {
    override val _uiState: MutableStateFlow<UiState>
        get() = TODO("Not yet implemented")

    override fun accept(event: UiEvent) {
        TODO("Not yet implemented")
    }
}
