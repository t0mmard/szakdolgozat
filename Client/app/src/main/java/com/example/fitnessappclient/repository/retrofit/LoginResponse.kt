package com.example.fitnessappclient.repository.retrofit

import com.example.fitnessappclient.repository.entities.User

data class LoginResponse (
    val response : String,
    val success : Boolean,
    val user : User?
)