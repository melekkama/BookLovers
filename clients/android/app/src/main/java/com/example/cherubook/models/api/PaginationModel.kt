package com.example.cherubook.models.api

import com.google.gson.annotations.SerializedName

data class PaginationModel<T> (
    @SerializedName("pageNumber"      ) var pageNumber      : Int,
    @SerializedName("pageSize"        ) var pageSize        : Int,
    @SerializedName("totalCount"      ) var totalCount      : Int,
    @SerializedName("totalPages"      ) var totalPages      : Int,
    @SerializedName("hasPreviousPage" ) var hasPreviousPage : Boolean,
    @SerializedName("hasNextPage"     ) var hasNextPage     : Boolean,
    @SerializedName("data"            ) var data            : ArrayList<T> = arrayListOf()
)