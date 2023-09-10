package com.wreckingball.imbored.network

import com.wreckingball.imbored.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class PexelAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", BuildConfig.PEXEL_AUTH_KEY)
            .build()
        return chain.proceed(request)
    }
}