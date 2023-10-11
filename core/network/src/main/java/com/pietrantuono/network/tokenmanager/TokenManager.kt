package com.pietrantuono.network.tokenmanager

interface TokenManager {

    fun getToken(): String?

    fun setToken(token: String)

    fun getDeviceId(): String

}