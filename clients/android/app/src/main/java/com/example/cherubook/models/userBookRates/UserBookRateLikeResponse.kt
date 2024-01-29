package com.example.cherubook.models.userBookRates

import com.google.gson.annotations.SerializedName

data class UserBookRateLikeResponse (
    @SerializedName("id"             ) var id             : String,
    @SerializedName("type"           ) var type           : Boolean,
    @SerializedName("isLike"         ) var isLike         : Boolean,
    @SerializedName("isDislike"      ) var isDislike      : Boolean,
    @SerializedName("isDeleted"      ) var isDeleted      : Boolean,
    @SerializedName("userBookRateId" ) var userBookRateId : String,
    @SerializedName("userId"         ) var userId         : String,
    @SerializedName("oldType"        ) var oldType        : Boolean?=null
)
