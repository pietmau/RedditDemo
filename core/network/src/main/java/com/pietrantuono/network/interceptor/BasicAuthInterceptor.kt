package com.pietrantuono.network.interceptor

import android.util.Base64
import com.pietrantuono.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor constructor(
    private val host: String = HOST,
    private val credentials: String = "${BuildConfig.CLIENT_ID}:"
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.url.host != host) {
            return chain.proceed(request)
        }
        val encodedCredentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        val newRequest = request.newBuilder()
            .header(AUTHORIZATION, "$BASIC $encodedCredentials").build()
        return chain.proceed(newRequest)
    }

    private companion object {
        private const val HOST = "www.reddit.com"
        private const val AUTHORIZATION = "Authorization"
        private const val BASIC = "Basic"
    }
}
