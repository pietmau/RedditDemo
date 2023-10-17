package com.pietrantuono.detail.presentation.viewmodel

import com.pietrantuono.common.Mapper
import javax.inject.Inject

// Errors messages should be mapped to a user friendly message.
class ErrorMapper @Inject constructor() : Mapper<String?, String> {
    override fun map(input: String?) = AN_ERROR_OCCURRED

    private companion object {
        private const val AN_ERROR_OCCURRED = "An error occurred"
    }
}
