package com.example.cherubook.models.userBookRates

import com.google.gson.annotations.SerializedName

data class UserBookRateUpdateRequest (
    @SerializedName("userBookRate" ) var userBookRate : String,
    @SerializedName("user"         ) var user         : String,
    @SerializedName("type"         ) var type         : Boolean
)