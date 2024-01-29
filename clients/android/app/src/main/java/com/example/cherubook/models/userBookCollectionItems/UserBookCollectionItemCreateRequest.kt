package com.example.cherubook.models.userBookCollectionItems

import com.google.gson.annotations.SerializedName

data class UserBookCollectionItemCreateRequest (
    @SerializedName("userBookCollectionId" ) var userBookCollectionId : String,
    @SerializedName("bookId"               ) var bookId               : String
)