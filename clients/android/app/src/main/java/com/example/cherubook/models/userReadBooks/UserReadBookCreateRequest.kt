package com.example.cherubook.models.userReadBooks

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserReadBookCreateRequest (
    @SerializedName("book"   ) var book   : String,
    @SerializedName("readAt" ) var readAt : Date
)