package com.example.cherubook.services.retrofitServices

import com.example.cherubook.models.api.ApiResponse
import com.example.cherubook.models.userBookCollections.UserBookCollection
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitUserCollectionService {
    @GET("/api/userBookCollections")
    suspend fun getUserBookCollections():Response<ApiResponse<Array<UserBookCollection>>>
}