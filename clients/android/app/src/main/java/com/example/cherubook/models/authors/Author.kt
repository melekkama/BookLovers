package com.example.cherubook.models.authors

import com.google.gson.annotations.SerializedName

data class Author (
    @SerializedName("id"       ) var id       : String,
    @SerializedName("name"     ) var name     : String,
    @SerializedName("surname"  ) var surname  : String,
    @SerializedName("fullName" ) var fullName : String,
    @SerializedName("photo"    ) var photo    : String? = null
)