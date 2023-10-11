package com.pietrantuono.network.interceptor

import com.pietrantuono.common.Logger
import com.pietrantuono.network.api.accesstoken.RetrofitAccessTokenApiClient
import com.pietrantuono.network.tokenmanager.TokenManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BearerTokenAuthInterceptor constructor(
    private val host: String = HOST,
    private val tokenManager: TokenManager,
    private val accessTokenApiClient: RetrofitAccessTokenApiClient,
    private val logger: Logger
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.url.host != host) {
            return chain.proceed(request)
        }
        val token = getTokenFromStore() ?: return getNewTokenAndDoRequest(request, chain)
        val response = doRequest(request, token, chain)
        return response.takeIf { response.code != UNAUTHORIZED } ?: getNewTokenAndDoRequest(request, chain)
    }

    private fun getNewTokenAndDoRequest(request: Request, chain: Interceptor.Chain): Response {
        val token = getNewToken() ?: return chain.proceed(request)
        return doRequest(request, token, chain)
    }

    private fun doRequest(request: Request, token: String, chain: Interceptor.Chain): Response {
        val newRequest = request.newBuilder().header(AUTHORIZATION, "$BEARER $token").build()
        return chain.proceed(newRequest)
    }

    private fun getTokenFromStore() = tokenManager.getToken()

    private fun getNewToken(): String? =
        try {
            val deviceId = tokenManager.getDeviceId()
            val token = accessTokenApiClient.getAccessToken(deviceId)
            token?.accessToken?.also { tokenManager.setToken(it) }
        } catch (e: Exception) {
            logger.logException(e)
            null
        }

    private companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
        private const val UNAUTHORIZED = 401
        private const val HOST = "oauth.reddit.com"
    }
}
