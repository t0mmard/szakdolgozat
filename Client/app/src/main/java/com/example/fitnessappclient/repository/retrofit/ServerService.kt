package com.example.fitnessappclient.repository.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServerService {
    @GET("/")
    fun getRandom() : Call<Random>

    @POST("/check_login")
    fun login(@Body authenticationData: AuthenticationData ) : Call<LoginResponse>

}