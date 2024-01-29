package com.example.cherubook.models.userReadBooks

import com.example.cherubook.models.books.Book
import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserReadBook (
    @SerializedName("id"     ) var id     : String,
    @SerializedName("readAt" ) var readAt : Date,
    @SerializedName("book"   ) var book   : Book?   = null,
    @SerializedName("bookId" ) var bookId : String,
    @SerializedName("userId" ) var userId : String
)