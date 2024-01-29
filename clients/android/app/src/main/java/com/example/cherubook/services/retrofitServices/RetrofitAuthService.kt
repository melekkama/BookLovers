package com.example.cherubook.services.retrofitServices

import com.example.cherubook.models.api.ApiResponse
import com.example.cherubook.models.users.Profile
import com.example.cherubook.models.users.requests.UserSignInRequest
import com.example.cherubook.models.users.requests.UserSignUpRequest
import com.example.cherubook.models.users.responses.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitAuthService {
    @POST("api/users")
    suspend fun signUp(@Body model: UserSignUpRequest): Response<ApiResponse<Profile>>

    @POST("api/auth")
    suspend fun signIn(@Body model: UserSignInRequest): Response<ApiResponse<SignInResponse>>
}