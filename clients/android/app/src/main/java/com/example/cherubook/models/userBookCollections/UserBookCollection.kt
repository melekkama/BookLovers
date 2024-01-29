package com.example.cherubook.models.userBookCollections

import com.example.cherubook.models.books.Book
import com.example.cherubook.models.users.Profile
import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserBookCollection (
    @SerializedName("id"         ) var id         : String,
    @SerializedName("name"       ) var name       : String,
    @SerializedName("createdAt"  ) var createdAt  : Date,
    @SerializedName("updatedAt"  ) var updatedAt  : Date,
    @SerializedName("itemsCount" ) var itemsCount : Int,
    @SerializedName("items"      ) var items      : ArrayList<Book> = arrayListOf(),
    @SerializedName("user"       ) var user       : Profile?   = null,
    @SerializedName("userId"     ) var userId     : String? = null
)