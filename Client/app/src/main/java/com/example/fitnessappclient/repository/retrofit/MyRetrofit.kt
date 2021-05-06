package com.example.fitnessappclient.repository.retrofit

import android.telephony.ims.RegistrationManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofit {

    var username = ""
    var password = ""
    private var service : ServerService
    private var client : OkHttpClient

    private val logTag = "Server response"

    init {
        //autentikáció elkapására
        client = OkHttpClient
            .Builder()
            .addInterceptor( BasicAuthenticationInterceptor(username,password))
            .build()
        val gson : Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080") //TODO herokura rákötni !!!MÉG A HÉTEN!!!
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        service = retrofit.create(ServerService::class.java)
    }

    //bejelentkeztetés
    fun login(username : String, password : String, loginCallback : LoginCallback){
        val loginResponse = service.login(AuthenticationData(username,password))

        loginResponse.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    Log.i(logTag,"response | text: ${loginResponse.response} | successful: ${loginResponse.success} | userId: ${loginResponse.user?.userId?:"null"}")
                    if(loginResponse.success)loginCallback.onSucess(loginResponse)
                    else loginCallback.onFailure()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginCallback.onFailure()
            }
        })
    }

    //új felhasználó regisztrálása
    fun register(username : String, password: String, registerCallback: RegisterCallback){
        val registrationResponse = service.register(AuthenticationData(username, password))

        registrationResponse.enqueue(object : Callback<RegistrationResponse> {
            override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {

                val registrationResponse = response.body()
                if (registrationResponse != null) {
                    Log.i(logTag,"response text: ${registrationResponse.response} successful: ${registrationResponse.success}")
                   if(registrationResponse.success) registerCallback.onSucess()
                }
            }

            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
               registerCallback.onFailure()
            }
        })
    }

    //minden adat szinkronizálása
    fun fullSynchronization(fullSynchronizationData: FullSynchronizationData){
        val fullSynchronizationResponse = service.fullSynchronization(fullSynchronizationData)

        fullSynchronizationResponse.enqueue(object : Callback<SynchronizationResponse>{
            override fun onResponse(call: Call<SynchronizationResponse>, response: Response<SynchronizationResponse>) {
                println(response)

                val registrationResponse = response.body()
                if (registrationResponse != null) {
                    println(registrationResponse.response)
                }
            }

            override fun onFailure(call: Call<SynchronizationResponse>, t: Throwable) {
                println("nem elérhető a szerver")
            }
        })
    }

}



//fun getAnswer() : LiveData<Random> {
//    val answer = service.getRandom()
//
//    var toReturn = MutableLiveData<Random>()
//
//    answer.enqueue( object : Callback<Random> {
//        override fun onResponse(call: Call<Random>, response: Response<Random>) {
//            if(!response.isSuccessful){
//                toReturn.postValue(Random("notSuccessful", listOf()))
//            }
//            val random = response.body()
//            if(random != null) {
//                toReturn.postValue(random!!)
//                println(random.string)
//            }
//        }
//
//        override fun onFailure(call: Call<Random>, t: Throwable) {
//            toReturn.postValue(Random(t.message!!, listOf()))
//        }
//    })
//
//    return toReturn
//}