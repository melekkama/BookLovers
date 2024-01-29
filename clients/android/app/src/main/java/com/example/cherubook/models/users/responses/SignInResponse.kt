package com.example.cherubook.models.users.responses

import com.example.cherubook.models.users.JwtToken
import com.example.cherubook.models.users.Profile
import com.google.gson.annotations.SerializedName

data class SignInResponse (
    @SerializedName("token"   ) var token   : JwtToken,
    @SerializedName("profile" ) var profile : Profile,

)