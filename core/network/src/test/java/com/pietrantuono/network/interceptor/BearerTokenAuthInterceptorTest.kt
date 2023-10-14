package com.pietrantuono.network.interceptor

import com.google.common.truth.Truth.assertThat
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
    private val newRequest = mockk<Request>()
    private val builder = mockk<Request.Builder>(relaxed = true) {
        every { header(any(), any()) } returns this
        every { build() } returns newRequest
    }
    private val request: Request = mockk {
        every { url } returns mockk {
            every { host } returns HOST
        }
        every { newBuilder() } returns builder
    }
    private val response: Response = mockk(relaxed = true)
    private val chain: Chain = mockk {
        every { proceed(any()) } returns response
        every { request() } returns request
    }
    private val tokenManager: TokenManager = mockk {
        every { getStoredToken() } returns TOKEN
        every { getNewToken() } returns TOKEN
    }
    private val bearerTokenAuthInterceptor = BearerTokenAuthInterceptor(
        HOST,
        tokenManager
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
    fun `given when request url host is not equal to host when called then does a new request`() {
        // When
        val actual = bearerTokenAuthInterceptor.intercept(chain)

        // Then
        verifyMakeNewRequest(actual)
    }

    @Test
    fun `given token is not stored when called then gets a new token`() {
        // Given
        every { tokenManager.getStoredToken() } returns null

        // When
        val actual = bearerTokenAuthInterceptor.intercept(chain)

        // Then
        verifyGetNewTokenAndMakeNewRequest(actual)
    }

    @Test
    fun `given token is stored but expired when called then then gets a new token`() {
        // Given
        every { response.code } returns UNAUTHORIZED

        // When
        val actual = bearerTokenAuthInterceptor.intercept(chain)

        // Then
        verifyGetNewTokenAndMakeNewRequest(actual)
    }

    private fun verifyGetNewTokenAndMakeNewRequest(actual: Response) {
        verify {
            tokenManager.getStoredToken()
            tokenManager.getNewToken()
        }
        verifyMakeNewRequest(actual)
    }

    private fun verifyMakeNewRequest(actual: Response) {
        verify {
            tokenManager.getStoredToken()
            request.newBuilder()
            builder.header(any(), any())
            builder.build()
            chain.proceed(newRequest)
        }
        assertThat(actual).isEqualTo(response)
    }

    private companion object {
        private const val HOST = "oauth.reddit.com"
        private const val GOOGLE = "http://www.google.com"
        private const val TOKEN = "token"
        private const val UNAUTHORIZED = 401
    }
}
