package com.example.fitnessappclient.repository.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit(val username : String, val password : String) {

    private val service : ServerService
    private val client : OkHttpClient

    init {
        //autentikáció elkapására
        client = OkHttpClient
            .Builder()
            .addInterceptor( BasicAuthenticationInterceptor(username,password))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080") //TODO herokura rákötni
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ServerService::class.java)
    }

    fun getAnswer() : LiveData<Random> {
        val answer = service.getRandom()

        var toReturn = MutableLiveData<Random>()

        answer.enqueue( object : Callback<Random> {
            override fun onResponse(call: Call<Random>, response: Response<Random>) {
                if(!response.isSuccessful){
                    toReturn.postValue(Random("notSuccessful", listOf()))
                }
                val random = response.body()
                if(random != null) {
                    toReturn.postValue(random!!)
                    println(random.string)
                }
            }

            override fun onFailure(call: Call<Random>, t: Throwable) {
                toReturn.postValue(Random(t.message!!, listOf()))
            }
        })

        return toReturn
    }
}