package com.pietrantuono.network.tokenmanager

import android.content.SharedPreferences
import com.pietrantuono.common.Logger
import com.pietrantuono.network.api.accesstoken.RetrofitAccessTokenApiClient
import java.util.UUID
import javax.inject.Inject

class SharedPreferencesTokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val accessTokenApiClient: RetrofitAccessTokenApiClient,
    private val logger: Logger
) : TokenManager {
    override fun getStoredToken(): String? = sharedPreferences.getString(TOKEN, null)

    override fun getNewToken(): String? =
        try {
            val deviceId = getDeviceId()
            val token = accessTokenApiClient.getAccessToken(deviceId)
            token?.accessToken?.let { setToken(it) }
            getStoredToken()
        } catch (e: Exception) {
            logger.logException(e)
            null
        }

    private fun setToken(token: String) {
        sharedPreferences.edit().putString(TOKEN, token).apply()
    }

    private fun getDeviceId() =
        sharedPreferences.getString(DEVICE_ID, null) ?: UUID.randomUUID().toString()
            .also { sharedPreferences.edit().putString(DEVICE_ID, it).apply() }

    private companion object {
        private const val TOKEN = "token"
        private const val DEVICE_ID = "device_id"
    }
}
