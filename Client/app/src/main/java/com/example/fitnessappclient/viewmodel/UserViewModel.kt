package com.example.fitnessappclient.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.fitnessappclient.repository.LocalDatabase
import com.example.fitnessappclient.repository.Repository
import com.example.fitnessappclient.repository.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    val allUsers : LiveData<List<User>>
    private val repository: Repository

    init{
        val userDao = LocalDatabase.getInstance(application).userDao
        repository = Repository(userDao)
        allUsers = repository.allUser

    }

    fun addUser(user:User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUser(user)
        }
    }

    fun isLoginSuccessful(userName : String, password: String) : Boolean{
        //TODO : bejelentkezés szerverről
        return true
    }

    fun setUserLoggedIn(userId : Long, loggedIn : Boolean) :LiveData<Boolean>{
        val result =MutableLiveData<Boolean>()
        viewModelScope.launch {
            repository.setUserLoggedIn(userId, loggedIn)
            result.postValue(true)
        }
        return result
    }

    fun getUserNameByUserId(userId : Long) : LiveData<String>{
        return repository.getUserNameByUserId(userId)
    }

    fun getUserLoggedInByUserId(userId : Long) : LiveData<Boolean>{
        return repository.getUserLoggedInByUserId(userId)
    }

}