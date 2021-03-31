package com.example.fitnessappclient.repository.retrofit

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthenticationInterceptor(val username : String, val password: String)  : Interceptor {
    private lateinit var credentials : String

    init{
        credentials = Credentials.basic(username,password)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials)
            .build()
        return chain.proceed(authenticatedRequest)
    }
}