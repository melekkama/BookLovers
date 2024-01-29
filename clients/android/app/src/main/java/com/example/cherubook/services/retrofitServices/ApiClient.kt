package com.example.cherubook.services.retrofitServices

import com.example.cherubook.interceptors.NetworkInterceptor
import com.example.cherubook.interceptors.TokenInterceptor
import com.example.cherubook.utility.GsonHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
        fun <T> buildService(
            baseUrl: String,
            retrofitService: Class<T>,
            existInterceptor: Boolean
        ): T {
            var clientBuilder = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(NetworkInterceptor())

            if (existInterceptor) {
                clientBuilder.addInterceptor(TokenInterceptor())
            }

            return Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(GsonHelper.gson))
                .build()
                .create(retrofitService);
        }
}