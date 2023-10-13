package com.pietrantuono.network.networkchecker

interface NetworkChecker {

    suspend fun isNetworkAvailable(): Boolean
}
