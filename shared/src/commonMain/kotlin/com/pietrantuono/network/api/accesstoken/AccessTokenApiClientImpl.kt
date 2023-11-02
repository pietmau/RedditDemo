package com.pietrantuono.network.api.accesstoken

import com.pietrantuono.network.entity.accesstoken.AccessToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.submitForm
import io.ktor.http.URLProtocol
import io.ktor.http.parameters
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json

class AccessTokenApiClientImpl(private val clientId: String = EMPTY_STRING) : AccessTokenApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials(username = clientId, password = EMPTY_STRING)
                }
            }
        }
    }

    override suspend fun getAccessToken(deviceId: String): AccessToken? = client.submitForm(
        formParameters = parameters {
            append(GRANT_TYPE, GRANT_TYPE_TYPE)
            append(REDIRECT_URI, REDIRECT_URI_URI)
            append(DEVICE_ID, deviceId)
        }
    ) {
        url {
            host = HOST
            protocol = URLProtocol.HTTPS
            path(PATH)
        }
    }.body()

    private companion object {
        private const val HOST = "www.reddit.com/"
        private const val PATH = "api/v1/access_token"
        private const val GRANT_TYPE_TYPE = "https://oauth.reddit.com/grants/installed_client"
        private const val REDIRECT_URI_URI = "https://www.reddit.com"
        private const val DEVICE_ID = "device_id"
        private const val GRANT_TYPE = "grant_type"
        private const val REDIRECT_URI = "redirect_uri"
        private const val EMPTY_STRING = ""
    }
}
