package com.example.cherubook.services.apiServices

import com.example.cherubook.constants.ApiConstants
import com.example.cherubook.models.api.ApiResponse
import com.example.cherubook.models.api.PaginationModel
import com.example.cherubook.models.userBookRates.UserBookRate
import com.example.cherubook.models.userBookRates.UserBookRateLikeRequest
import com.example.cherubook.models.userBookRates.UserBookRateLikeResponse
import com.example.cherubook.services.retrofitServices.ApiClient
import com.example.cherubook.services.retrofitServices.RetrofitUserBookRateService
import com.example.cherubook.utility.ExceptionHelpers

object UserBookRateService {
    private var retrofitService =
        ApiClient.buildService(ApiConstants.webApiBaseUrl, RetrofitUserBookRateService::class.java, true)

    suspend fun getUserBookRates(pageNumber:Number, pageSize:Number): ApiResponse<PaginationModel<UserBookRate>> {
        try {
            val response = retrofitService.getUserBookRates(pageNumber, pageSize)
            if (!response.isSuccessful) return ExceptionHelpers.handleApiError(response)
            return response.body()!!
        } catch (ex: Exception) {
            return ExceptionHelpers.handleException(ex)
        }
    }

    suspend fun likeUserBookRate(userBookRateId:String, type:Boolean): ApiResponse<UserBookRateLikeResponse> {
        try {
            val response = retrofitService.likeUserBookRate(UserBookRateLikeRequest(userBookRateId,type))
            if (!response.isSuccessful) return ExceptionHelpers.handleApiError(response)
            return response.body()!!
        } catch (ex: Exception) {
            return ExceptionHelpers.handleException(ex)
        }
    }
}