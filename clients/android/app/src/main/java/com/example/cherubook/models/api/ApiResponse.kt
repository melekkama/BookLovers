package com.example.cherubook.models.api

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("data"         ) var data: T?       = null,
    @SerializedName("statusCode"   ) var statusCode: Int,
    @SerializedName("isSuccessful" ) var isSuccessful: Boolean,
    @SerializedName("error"        ) var error: Error? = null
){
    companion object{
        fun <T> create(data: T): ApiResponse<T> {
            return ApiResponse(data,200,true,null)
        }
        fun <T> create(statusCode: Int,error: Error): ApiResponse<T> {
            return ApiResponse(null,statusCode,false,error)
        }
    }
}
