package com.example.cherubook.models.userBookRates

import com.google.gson.annotations.SerializedName


data class UserBookRateLikeRequest(
    @SerializedName("userBookRate") var userBookRate : String,
    @SerializedName("type"        ) var type       : Boolean,
)