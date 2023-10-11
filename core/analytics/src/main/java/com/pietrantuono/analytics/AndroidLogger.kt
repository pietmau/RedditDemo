package com.pietrantuono.analytics

import com.pietrantuono.common.Logger
import javax.inject.Inject

class AndroidLogger @Inject constructor() : Logger {
    override fun logException(exception: Exception) {
        /* no-op */ //TODO
    }
}
