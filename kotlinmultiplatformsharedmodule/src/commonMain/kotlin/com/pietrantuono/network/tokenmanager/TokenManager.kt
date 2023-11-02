package com.pietrantuono.network.tokenmanager

interface TokenManager {

    fun getStoredToken(): String?

    suspend fun getNewToken(): String?
}
