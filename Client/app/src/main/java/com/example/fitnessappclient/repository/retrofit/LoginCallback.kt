package com.example.fitnessappclient.repository.retrofit

interface LoginCallback {
    fun onSucess(loginResponse: LoginResponse)
    fun onFailure()
}