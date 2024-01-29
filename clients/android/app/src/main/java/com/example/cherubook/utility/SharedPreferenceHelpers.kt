package com.example.cherubook.utility

import android.content.Context
import android.content.SharedPreferences
import com.example.cherubook.constants.SharedPreferenceConstants
import com.example.cherubook.models.users.JwtToken
import com.example.cherubook.models.users.Profile
import com.example.cherubook.models.users.responses.SignInResponse
import com.google.gson.Gson

object SharedPreferenceHelpers {

        val gson = Gson()
        private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
            val editor = this.edit()
            operation(editor)
            editor.apply()
        }
        private fun getSharedPreference(name: String) =
            MainApplication
                .applicationContext()
                .getSharedPreferences(name, Context.MODE_PRIVATE)

        operator fun SharedPreferences.set(key: String, value: Any?) {
            when (value) {
                is String? -> edit { it.putString(key, value) }
                is Int -> edit { it.putInt(key, value) }
                is Boolean -> edit { it.putBoolean(key, value) }
                is Float -> edit { it.putFloat(key, value) }
                is Long -> edit { it.putLong(key, value) }
                else -> edit { it.putString(key, gson.toJson(value)) }
            }
        }
        @Suppress("IMPLICIT_CAST_TO_ANY")
        inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
            return when (T::class) {
                String::class -> getString(key, defaultValue as? String)
                Int::class -> getInt(key, defaultValue as? Int ?: -1)
                Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false)
                Float::class -> getFloat(key, defaultValue as? Float ?: -1f)
                Long::class -> getLong(key, defaultValue as? Long ?: -1)
                else -> gson.fromJson(getString(key, null), T::class.java)
            } as T?
        }

        fun remove(name: String, key: String,) =
            this.getSharedPreference(name).edit {
                it.remove(key)
            }

        fun clear(name: String) =
            this.getSharedPreference(name).edit {
                it.clear()
            }

        fun setToken(token: JwtToken) {
            this.getSharedPreference(SharedPreferenceConstants.auth)[SharedPreferenceConstants.token] = token
        }

        fun getToken():JwtToken? =
            getSharedPreference(SharedPreferenceConstants.auth)[SharedPreferenceConstants.token]


        fun setProfile(profile: Profile) {
            this.getSharedPreference(SharedPreferenceConstants.auth)[SharedPreferenceConstants.profile] = profile
        }
        fun getProfile() :Profile? =
            getSharedPreference(SharedPreferenceConstants.auth)[SharedPreferenceConstants.profile]

        fun setAuth(signInResponse: SignInResponse?){
            if(signInResponse==null){
                clearAuth()
                return
            }
            setToken(signInResponse.token)
            setProfile(signInResponse.profile)
        }

        fun clearAuth() =
            clear(SharedPreferenceConstants.auth)

}