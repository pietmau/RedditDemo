package com.pietrantuono.network.interceptor

import com.google.common.truth.Truth.assertThat
import com.pietrantuono.common.Logger
import com.pietrantuono.network.api.accesstoken.RetrofitAccessTokenApiClient
import com.pietrantuono.network.tokenmanager.TokenManager
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response
import org.junit.Test

class BearerTokenAuthInterceptorTest {
    private val builder = mockk<Request.Builder>(relaxed = true) {
        every { header(any(), any()) } returns this
        every { build() } returns mockk()
    }
    private val request: Request = mockk() {
        every { url } returns mockk {
            every { host } returns HOST
        }
        every { newBuilder() } returns builder
    }
    private val response: Response = mockk(relaxed = true)
    private val chain: Chain = mockk() {
        every { proceed(any()) } returns response
        every { request() } returns request
    }
    private val tokenManager: TokenManager = mockk {
        every { getToken() } returns TOKEN
    }
    private val accessTokenApiClient: RetrofitAccessTokenApiClient = mockk()
    private val logger: Logger = mockk(relaxed = true)
    private val bearerTokenAuthInterceptor = BearerTokenAuthInterceptor(
        HOST,
        tokenManager,
        accessTokenApiClient,
        logger
    )

    @Test
    fun `given request url host is not equal to host when called then proceeds`() {
        // Given
        val request = Request.Builder().url(GOOGLE).build()
        every { chain.request() } returns request

        // When
        val actual = bearerTokenAuthInterceptor.intercept(chain)

        // Then
        verify { tokenManager wasNot Called }
        assertThat(actual).isEqualTo(response)
    }

    @Test
    fun `given when request url host is not equal to host when called then proceeds`() {
        // When
        val actual = bearerTokenAuthInterceptor.intercept(chain)

        // Then
        verify {
            tokenManager.getToken()
            request.newBuilder()
            builder.header(any(), any())
            builder.build()
        }
        assertThat(actual).isEqualTo(response)
    }

    private companion object {
        private const val HOST = "oauth.reddit.com"
        private const val GOOGLE = "http://www.google.com"
        private const val TOKEN = "token"
    }
}
