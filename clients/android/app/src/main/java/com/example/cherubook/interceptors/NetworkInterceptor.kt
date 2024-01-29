package com.example.cherubook.interceptors

import com.example.cherubook.exceptions.OfflineException
import com.example.cherubook.utility.LiveNetworkListener.isConnected
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) throw OfflineException();
        var req= chain.request();
        return chain.proceed(req);
    }
}