package com.example.cherubook.models.api

import com.google.gson.annotations.SerializedName

data class Error (
    @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf(),
    @SerializedName("isShow" ) var isShow : Boolean,
    @SerializedName("path"   ) var path   : String= String()
){
    companion object{
        fun create(path: String,vararg errors: String): Error {
            return Error(convertVarargToArrayList(*errors),true,path)
        }

       private fun convertVarargToArrayList(vararg errors: String): ArrayList<String> {
            val arrayList = ArrayList<String>()
            for (error in errors) {
                arrayList.add(error)
            }
            return arrayList
        }
    }
}
