package com.example.cherubook.models.users.requests

import android.util.Patterns
import com.example.cherubook.models.api.Error
import com.google.gson.annotations.SerializedName
data class UserSignInRequest(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String
) {
    fun isValid(): Pair<Boolean, Error?> {
        val emailValidation = validateEmail()
        val passwordValidation = validatePassword()

        return if (emailValidation.first && passwordValidation.first) {
            true to null
        } else {
            val errors = ArrayList<String>().apply {
                addErrorIfNotValid(emailValidation)
                addErrorIfNotValid(passwordValidation)
            }
            false to Error(errors, true, "UserSignInRequest")
        }
    }

    private fun validateEmail(): Pair<Boolean, String?> {
        val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return isValid to if (!isValid) "Email is not valid" else null
    }

    private fun validatePassword(): Pair<Boolean, String?> {
        val isValid = password.length >= 6
        return isValid to if (!isValid) "Password must be at least 6 characters" else null
    }

    private fun MutableList<String>.addErrorIfNotValid(validationResult: Pair<Boolean, String?>) {
        if(validationResult.first) return
        validationResult.second?.let { add(it) }
    }
}