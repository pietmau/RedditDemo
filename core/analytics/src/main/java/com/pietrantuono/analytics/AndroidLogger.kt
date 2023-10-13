package com.pietrantuono.analytics

import android.util.Log
import com.pietrantuono.common.Logger
import javax.inject.Inject

class AndroidLogger @Inject constructor() : Logger {
    override fun logException(exception: Exception) {
        Log.e("AndroidLogger", exception.message, exception)
    }
}
