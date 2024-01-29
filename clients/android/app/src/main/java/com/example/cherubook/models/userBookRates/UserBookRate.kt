package com.example.cherubook.models.userBookRates

import com.example.cherubook.models.books.Book
import com.example.cherubook.models.users.Profile
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

data class UserBookRate (
    @SerializedName("id"        )  var id        : String,
    @SerializedName("content"   )  var content   : String,
    @SerializedName("rate"      )  var rate      : Float,
    @SerializedName("like"      )  var like      : Int,
    @SerializedName("dislike"   )  var dislike   : Int,
    @SerializedName("createdAt" )  var createdAt : Date,
    @SerializedName("isUserLiked") var isUserLiked : Boolean? = null,
    @SerializedName("book"      )  var book      : Book?   = null,
    @SerializedName("bookId"    )  var bookId    : String,
    @SerializedName("user"      )  var user      : Profile?   = null,
    @SerializedName("userId"    )  var userId    : String
) : Serializable {
    companion object{
         fun getDummyUserBookRate():UserBookRate{
            return UserBookRate(
                "0",
                "",
                0.0f,
                0,
                0,
                Date(),
                null,
                null,
                "",
                null,
                ""
            )
        }
    }
}