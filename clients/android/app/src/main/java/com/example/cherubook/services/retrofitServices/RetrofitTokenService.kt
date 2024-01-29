package com.example.cherubook.services.retrofitServices

import com.example.cherubook.models.api.ApiResponse
import com.example.cherubook.models.tokens.RefreshTokenRequest
import com.example.cherubook.models.users.responses.SignInResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface RetrofitTokenService {
    @PUT("api/auth")
    fun refreshToken(
        @Body model: RefreshTokenRequest
    ): Call<ApiResponse<SignInResponse>>

    @PUT("api/auth")
    suspend fun refreshTokenAsync(
        @Body model: RefreshTokenRequest
    ): Response<ApiResponse<SignInResponse>>
}