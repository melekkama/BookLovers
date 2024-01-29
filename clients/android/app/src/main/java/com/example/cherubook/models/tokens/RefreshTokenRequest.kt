package com.example.cherubook.models.tokens

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refreshToken") var refreshToken: String,
)