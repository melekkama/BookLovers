package com.example.cherubook.services.retrofitServices

import com.example.cherubook.models.api.ApiResponse
import com.example.cherubook.models.api.PaginationModel
import com.example.cherubook.models.userBookRates.UserBookRate
import com.example.cherubook.models.userBookRates.UserBookRateLikeRequest
import com.example.cherubook.models.userBookRates.UserBookRateLikeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitUserBookRateService {
    @GET("api/userBookRates")
    suspend fun getUserBookRates(@Header("PageNumber") pageNumber:Number,@Header("PageSize") pageSize:Number): Response<ApiResponse<PaginationModel<UserBookRate>>>

    @POST("api/userBookRates/user/like")
    suspend fun likeUserBookRate(@Body userBookRateLikeRequest: UserBookRateLikeRequest): Response<ApiResponse<UserBookRateLikeResponse>>
}