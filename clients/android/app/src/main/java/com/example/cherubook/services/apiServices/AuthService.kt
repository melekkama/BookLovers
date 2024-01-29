package com.example.cherubook.services.apiServices

import android.content.Intent
import com.example.cherubook.constants.ApiConstants
import com.example.cherubook.models.api.ApiResponse
import com.example.cherubook.models.users.Profile
import com.example.cherubook.models.users.requests.UserSignInRequest
import com.example.cherubook.models.users.requests.UserSignUpRequest
import com.example.cherubook.models.users.responses.SignInResponse
import com.example.cherubook.services.retrofitServices.ApiClient
import com.example.cherubook.services.retrofitServices.RetrofitAuthService
import com.example.cherubook.ui.auth.AuthActivity
import com.example.cherubook.utility.ExceptionHelpers
import com.example.cherubook.utility.MainApplication
import com.example.cherubook.utility.SharedPreferenceHelpers

object AuthService {
    private var retrofitService =
        ApiClient.buildService(ApiConstants.webApiBaseUrl, RetrofitAuthService::class.java, false);

    suspend fun signUp(userSignUp: UserSignUpRequest): ApiResponse<Profile> {
        try {
            var response = retrofitService.signUp(userSignUp);
            if (!response.isSuccessful) return ExceptionHelpers.handleApiError(response);
            return response.body() as ApiResponse<Profile>;
        } catch (ex: Exception) {
            return ExceptionHelpers.handleException(ex);
        }
    }

    suspend fun signIn(userSignIn: UserSignInRequest): ApiResponse<SignInResponse> {
        try {
            var response = retrofitService.signIn(userSignIn);
            if (!response.isSuccessful) return ExceptionHelpers.handleApiError(response);
            var signInResponse = response.body() as ApiResponse<SignInResponse>;
            if(signInResponse.data == null) return ExceptionHelpers.handleApiError(response);
            SharedPreferenceHelpers.setAuth(signInResponse.data)
            return signInResponse
        } catch (ex: Exception) {
            return ExceptionHelpers.handleException(ex);
        }
    }

    fun logOut(){
        SharedPreferenceHelpers.clearAuth()
        var intent = Intent(MainApplication.applicationContext(),AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        MainApplication.applicationContext().startActivity(intent)

    }
}