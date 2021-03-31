package com.example.fitnessappclient.repository.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ServerService {
    @GET("/")
    fun getRandom() : Call<Random>


//    @GET("users/{user}/repos")
//    fun listRepos(@Path("user") user: String?): Call<List<Repo?>?>?

}