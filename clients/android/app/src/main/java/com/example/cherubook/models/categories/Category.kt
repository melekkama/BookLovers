package com.example.cherubook.models.categories

import com.google.gson.annotations.SerializedName
data class Category (
    @SerializedName("id"   ) var id   : String,
    @SerializedName("name" ) var name : String
)