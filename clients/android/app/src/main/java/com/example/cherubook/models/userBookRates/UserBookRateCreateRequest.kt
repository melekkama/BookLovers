package com.example.cherubook.models.userBookRates

import com.google.gson.annotations.SerializedName

data class UserBookRateCreateRequest (
    @SerializedName("book"    ) var book    : String,
    @SerializedName("content" ) var content : String,
    @SerializedName("rate"    ) var rate    : Int
)