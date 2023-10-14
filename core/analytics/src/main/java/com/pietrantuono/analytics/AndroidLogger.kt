package com.pietrantuono.analytics

import android.util.Log
import com.pietrantuono.common.Logger
import com.pietrantuono.mylibrary.BuildConfig
import javax.inject.Inject

class AndroidLogger @Inject constructor() : Logger {

    override fun logException(exception: Exception) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, exception.message, exception)
        }
    }

    private companion object {
        private const val TAG = "AndroidLogger"
    }
}
