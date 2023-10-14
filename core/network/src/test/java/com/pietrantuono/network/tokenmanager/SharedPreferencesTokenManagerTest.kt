package com.pietrantuono.network.tokenmanager

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.common.truth.Truth.assertThat
import com.pietrantuono.common.Logger
import com.pietrantuono.network.api.accesstoken.RetrofitAccessTokenApiClient
import com.pietrantuono.network.entity.accesstoken.AccessToken
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SharedPreferencesTokenManagerTest {
    private val editor = mockk<Editor>(relaxed = true) {
        every { putString(any(), any()) } returns this
    }
    private val sharedPreferences: SharedPreferences = mockk(relaxed = true) {
        every { getString(DEVICE_ID, isNull()) } returns SOME_ID
        every { getString(TOKEN, isNull()) } returns SOME_TOKEN
        every { edit() } returns editor
    }
    private val accessToken = mockk<AccessToken>(relaxed = true) {
        every { accessToken } returns SOME_TOKEN
    }
    private val accessTokenApiClient: RetrofitAccessTokenApiClient = mockk(relaxed = true) {
        every { getAccessToken(SOME_ID) } returns accessToken
    }
    private val logger: Logger = mockk(relaxed = true)

    private val sharedPreferencesTokenManager = SharedPreferencesTokenManager(
        sharedPreferences,
        accessTokenApiClient,
        logger
    )

    @Test
    fun `when getNewToken is called then calls the remote`() {
        // When
        val actual = sharedPreferencesTokenManager.getNewToken()

        // Then
        verify {
            accessTokenApiClient.getAccessToken(SOME_ID)
            sharedPreferences.edit()
            editor.putString(TOKEN, SOME_TOKEN)
            editor.apply()
        }
        assertThat(actual).isEqualTo(SOME_TOKEN)
    }

    private companion object {
        private const val TOKEN = "token"
        private const val SOME_TOKEN = "some_token"
        private const val DEVICE_ID = "device_id"
        private const val SOME_ID = "id"
    }
}
