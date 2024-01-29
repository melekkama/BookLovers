package com.example.cherubook.utility

import com.google.gson.GsonBuilder

object GsonHelper {
    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create()!!
}