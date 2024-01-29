package com.example.cherubook.models.users

import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("id") var id: String? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("surname") var surname: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("fullName") var fullName: String? = null,
    @SerializedName("userName") var userName: String? = null,
    @SerializedName("photo") var photo: String? = null,
    @SerializedName("banner") var banner: String? = null,
    @SerializedName("about") var about: String? = null,

)