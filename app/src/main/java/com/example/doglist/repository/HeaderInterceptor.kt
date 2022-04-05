package com.example.doglist.repository

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Accept:", "application/json")
            .addHeader("ApiKey", "1249r9879287948701")
            .build()

        return chain.proceed(request)
    }
}