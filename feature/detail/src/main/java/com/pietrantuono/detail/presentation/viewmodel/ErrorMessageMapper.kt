package com.pietrantuono.detail.presentation.viewmodel

import com.pietrantuono.common.Mapper

class ErrorMessageMapper : Mapper<String?, String> {

    // It should map to a user friendly message instead.
    override fun map(input: String?) = input ?: UNKNOWN_ERROR

    private companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}
