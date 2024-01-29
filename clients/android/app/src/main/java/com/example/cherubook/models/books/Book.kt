package com.example.cherubook.models.books

import com.example.cherubook.models.authors.Author
import com.example.cherubook.models.categories.Category
import com.example.cherubook.models.publishers.Publisher
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Book (

    @SerializedName("id"          ) var id          : String,
    @SerializedName("name"        ) var name        : String,
    @SerializedName("isbn"        ) var isbn        : String,
    @SerializedName("page"        ) var page        : Int,
    @SerializedName("description" ) var description : String,
    @SerializedName("image"       ) var image       : String,
    @SerializedName("rate"        ) var rate        : Float,
    @SerializedName("authorId"    ) var authorId    : String,
    @SerializedName("categoryId"  ) var categoryId  : String,
    @SerializedName("publisherId" ) var publisherId : String,
    @SerializedName("author"      ) var author      : Author?    = null,
    @SerializedName("category"    ) var category    : Category?  = null,
    @SerializedName("publisher"   ) var publisher   : Publisher? = null

):Serializable{
    companion object{
        fun getDummyBook():Book{
            return Book(
                "0",
                "",
                "",
                0,
                "",
                "",
                0.0f,
                "",
                "",
                "",
                null,
                null,
                null
            )
        }
    }
}