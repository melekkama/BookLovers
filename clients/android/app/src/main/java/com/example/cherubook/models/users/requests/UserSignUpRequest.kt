package com.example.cherubook.models.users.requests

import com.example.cherubook.models.api.Error
import com.google.gson.annotations.SerializedName
data class UserSignUpRequest (
    @SerializedName("name"     ) var name     : String,
    @SerializedName("surname"  ) var surname  : String,
    @SerializedName("email"    ) var email    : String,
    @SerializedName("userName" ) var userName : String,
    @SerializedName("password" ) var password : String
){

    fun isValid():Pair<Boolean,Error?>{
        val nameValidation=validateName()
        val surnameValidation=validateSurname()
        val emailValidation=validateEmail()
        val userNameValidation=validateUserName()
        val passwordValidation=validatePassword()

        return if(nameValidation.first && surnameValidation.first && emailValidation.first && userNameValidation.first && passwordValidation.first){
            true to null
        }else{
            val errors=ArrayList<String>().apply {
                addErrorIfNotValid(emailValidation)
                addErrorIfNotValid(userNameValidation)
                addErrorIfNotValid(nameValidation)
                addErrorIfNotValid(surnameValidation)
                addErrorIfNotValid(passwordValidation)
            }
            false to Error(errors,true,"UserSignUpRequest")
        }
    }

    private fun validateName():Pair<Boolean,String?>{
        val isValid=name.length>=2
        return isValid to if(!isValid) "Name must be at least 2 characters" else null
    }

    private fun validateSurname():Pair<Boolean,String?>{
        val isValid=surname.length>=2
        return isValid to if(!isValid) "Surname must be at least 2 characters" else null
    }

    private fun validateEmail():Pair<Boolean,String?>{
        val isValid=android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return isValid to if(!isValid) "Email is not valid" else null
    }

    private fun validateUserName():Pair<Boolean,String?>{
        val isValid=userName.length>=2
        return isValid to if(!isValid) "Username must be at least 2 characters" else null
    }

    private fun validatePassword():Pair<Boolean,String?>{
        val isValid=password.length>=6
        return isValid to if(!isValid) "Password must be at least 6 characters" else null
    }

    private fun MutableList<String>.addErrorIfNotValid(validationResult:Pair<Boolean,String?>){
        if(validationResult.first) return
        validationResult.second?.let { add(it) }
    }
}