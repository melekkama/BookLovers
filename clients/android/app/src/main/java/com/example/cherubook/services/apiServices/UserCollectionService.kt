package com.example.cherubook.services.apiServices

import com.example.cherubook.constants.ApiConstants
import com.example.cherubook.models.api.ApiResponse
import com.example.cherubook.models.userBookCollections.UserBookCollection
import com.example.cherubook.services.retrofitServices.ApiClient
import com.example.cherubook.services.retrofitServices.RetrofitUserCollectionService
import com.example.cherubook.utility.ExceptionHelpers

object UserCollectionService {
    private var retrofitService =
        ApiClient.buildService(ApiConstants.webApiBaseUrl, RetrofitUserCollectionService::class.java, true)

    suspend fun getUserCollections(): ApiResponse<Array<UserBookCollection>> {
        try {
            val response = retrofitService.getUserBookCollections()
            if (!response.isSuccessful) return ExceptionHelpers.handleApiError(response)
            return response.body()!!
        } catch (ex: Exception) {
            return ExceptionHelpers.handleException(ex)
        }
    }
}