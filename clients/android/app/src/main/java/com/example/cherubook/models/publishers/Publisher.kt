package com.example.cherubook.models.publishers

import com.google.gson.annotations.SerializedName

data class Publisher (
    @SerializedName("id"   ) var id   : String,
    @SerializedName("name" ) var name : String
)