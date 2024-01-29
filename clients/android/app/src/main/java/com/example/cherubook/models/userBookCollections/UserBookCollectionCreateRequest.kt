package com.example.cherubook.models.userBookCollections

import com.google.gson.annotations.SerializedName

data class UserBookCollectionCreateRequest (
    @SerializedName("name") var name : String
)