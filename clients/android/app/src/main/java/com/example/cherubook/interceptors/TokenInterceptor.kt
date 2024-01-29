package com.example.cherubook.interceptors

import android.content.Intent
import android.util.Log
import com.example.cherubook.models.users.JwtToken
import com.example.cherubook.services.apiServices.TokenService
import com.example.cherubook.ui.auth.AuthActivity
import com.example.cherubook.utility.MainApplication
import com.example.cherubook.utility.SharedPreferenceHelpers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = SharedPreferenceHelpers.getToken()

        if (token?.accessToken != null) {
            Log.i("OkHttp", "Request header added access token")
            val req = newRequestWithAccessToken(request, token.accessToken)
            var res = chain.proceed(req)

            if (res.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Log.i("OkHttp", "Access Token fell into invalid 401 status")
                res = handleUnauthorized(token, chain)
            }
            return res
        }
        return chain.proceed(request)
    }

    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
    }

    private fun handleUnauthorized(token: JwtToken?, chain: Interceptor.Chain): Response {
        if (token == null) return chain.proceed(chain.request()).logout()

        val refreshTokenRequestResponse = TokenService.refreshToken(token.refreshToken)
        if (!refreshTokenRequestResponse.isSuccessful) return chain.proceed(chain.request()).logout()

        val newToken = SharedPreferenceHelpers.getToken()
        if (newToken?.accessToken == null) return chain.proceed(chain.request()).logout()

        val newRequest = newRequestWithAccessToken(chain.request(), newToken.accessToken)
        var newResponse = chain.proceed(newRequest)

        if (newResponse.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            newResponse.logout()
        }
        return newResponse
    }

    private fun Response.logout(): Response {
        Log.i("OkHttp", "Logout")
        SharedPreferenceHelpers.clearAuth()
        val intent = Intent(MainApplication.applicationContext(), AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        MainApplication.applicationContext().startActivity(intent)
        return this
    }
}