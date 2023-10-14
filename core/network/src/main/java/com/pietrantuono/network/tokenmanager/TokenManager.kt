package com.pietrantuono.network.tokenmanager

interface TokenManager {

    fun getStoredToken(): String?

    fun getNewToken(): String?
}
