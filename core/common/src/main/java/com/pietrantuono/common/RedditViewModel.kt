package com.pietrantuono.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.util.function.Consumer
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class RedditViewModel<UiState, UiEvent>(
    private val coroutineContext: CoroutineContext,
    private val logger: Logger
) : ViewModel(), Consumer<UiEvent> {

    @Suppress("PropertyName")
    protected abstract val _uiState: MutableStateFlow<UiState>
    val uiState: Flow<UiState>
        get() = _uiState

    val latestState: UiState
        get() = _uiState.value

    protected fun launch(
        onError: (Exception) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(coroutineContext) {
            try {
                block()
            } catch (exception: Exception) {
                onError(exception)
                logger.logException(exception)
            }
        }
    }

    protected fun updateState(reducer: UiState.() -> UiState) {
        val currentState = _uiState.value ?: return
        val newState = currentState.reducer()
        if (newState != currentState) {
            _uiState.value = newState
        }
    }
}
