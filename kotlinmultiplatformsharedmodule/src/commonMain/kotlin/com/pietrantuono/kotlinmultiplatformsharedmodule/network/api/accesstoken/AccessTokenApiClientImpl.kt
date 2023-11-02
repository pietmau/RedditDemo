package com.pietrantuono.kotlinmultiplatformsharedmodule.network.api.accesstoken

import com.pietrantuono.network.api.accesstoken.AccessTokenApiClient
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
import kotlin.io.encoding.Base64

class AccessTokenApiClientImpl : AccessTokenApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials(username = "KYewjf56pgiKLzVihGALlg", password = "")
                }
            }
        }
    }

    override suspend fun getAccessToken(deviceId: String): AccessToken? {
        val response: AccessToken? = client.submitForm(
            formParameters = parameters {
                append("grant_type", GRANT_TYPE)
                append("redirect_uri", REDIRECT_URI)
                append("device_id", "adefgtdgbtdfgyhescftg") // TODO
            }
        ) {
            url {
                protocol = URLProtocol.HTTPS
                host = "www.reddit.com/"
                path("api/v1/access_token")
            }
        }.body()
        return response
    }

    private companion object {
        private const val URL = "https://www.reddit.com/api/v1/access_token"
        private const val GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client"
        private const val REDIRECT_URI = "https://www.reddit.com"
    }
}
