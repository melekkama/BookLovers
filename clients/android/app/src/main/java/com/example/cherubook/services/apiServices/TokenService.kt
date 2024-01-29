package com.example.cherubook.services.apiServices

import com.example.cherubook.constants.ApiConstants
import com.example.cherubook.models.api.ApiResponse
import com.example.cherubook.models.api.Error
import com.example.cherubook.models.tokens.RefreshTokenRequest
import com.example.cherubook.models.users.responses.SignInResponse
import com.example.cherubook.services.retrofitServices.ApiClient
import com.example.cherubook.services.retrofitServices.RetrofitTokenService
import com.example.cherubook.utility.ExceptionHelpers
import com.example.cherubook.utility.SharedPreferenceHelpers
import kotlinx.coroutines.delay
import java.net.HttpURLConnection

object TokenService {
    private var retrofitService =
        ApiClient.buildService(ApiConstants.webApiBaseUrl, RetrofitTokenService::class.java, false)
    fun refreshToken(refreshToken: String): ApiResponse<SignInResponse> {
        try {
            var response = retrofitService.refreshToken(RefreshTokenRequest(refreshToken)).execute()
            if (!response.isSuccessful) return ExceptionHelpers.handleApiError(response)
            var apiResponse = response.body() as ApiResponse<SignInResponse>
            if (apiResponse.data == null) return ExceptionHelpers.handleApiError(response)
            SharedPreferenceHelpers.setAuth(apiResponse.data)
            return apiResponse
        } catch (ex: Exception) {
            return ExceptionHelpers.handleException(ex)
        }
    }

      private suspend fun refreshTokenAsync(refreshToken: String): ApiResponse<SignInResponse> {
        try {
            var response = retrofitService.refreshTokenAsync(RefreshTokenRequest(refreshToken))
            if (!response.isSuccessful) return ExceptionHelpers.handleApiError(response)
            var apiResponse = response.body() as ApiResponse<SignInResponse>
            if (apiResponse.data == null) return ExceptionHelpers.handleApiError(response)
            SharedPreferenceHelpers.setAuth(apiResponse.data)
            return apiResponse
        } catch (ex: Exception) {
            return ExceptionHelpers.handleException(ex)
        }
    }
    suspend fun refreshToken(): ApiResponse<SignInResponse> {
        var token = SharedPreferenceHelpers.getToken()
            ?: return ApiResponse.create(HttpURLConnection.HTTP_UNAUTHORIZED, Error.create("TokenService", "Token is null"))
        return refreshTokenAsync(token.refreshToken)
    }
}