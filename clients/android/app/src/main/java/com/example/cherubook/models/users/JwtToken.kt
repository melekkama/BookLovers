package com.example.cherubook.models.users

import com.google.gson.annotations.SerializedName

data class JwtToken(
    @SerializedName("accessToken") var accessToken: String,
    @SerializedName("refreshToken") var refreshToken: String,
)