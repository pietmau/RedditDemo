package com.pietrantuono.network.interceptor

import com.pietrantuono.network.tokenmanager.TokenManager
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class BearerTokenAuthInterceptor(
    private val host: String = HOST,
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        if (request.url.host != host) {
            return chain.proceed(request)
        }
        val token = tokenManager.getStoredToken() ?: return getNewTokenAndDoRequest(request, chain)
        val response = makeNewRequest(request, token, chain)
        return response.takeIf {
            response.code != UNAUTHORIZED
        } ?: getNewTokenAndDoRequest(request, chain)
    }

    private fun getNewTokenAndDoRequest(
        request: Request,
        chain: Chain
    ): Response {
        val token = tokenManager.getNewToken() ?: return chain.proceed(request)
        return makeNewRequest(request, token, chain)
    }

    private fun makeNewRequest(
        request: Request,
        token: String,
        chain: Chain
    ): Response {
        val newRequest = request.newBuilder().header(AUTHORIZATION, "$BEARER $token").build()
        return chain.proceed(newRequest)
    }

    private companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
        private const val UNAUTHORIZED = 401
        private const val HOST = "oauth.reddit.com"
    }
}
