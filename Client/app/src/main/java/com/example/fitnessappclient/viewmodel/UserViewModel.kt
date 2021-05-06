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

    fun startup(){
        viewModelScope.launch {
            repository.startup()
        }
    }

    fun getAllUsersFromDB() : LiveData<List<User>>{
        return repository.getAllUsers()
    }

    fun getUserByUserId(userId: Long)  : LiveData<User>{
        return repository.getUserByUserId(userId)
    }

    fun addUser(user:User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUser(user)
        }
    }

    fun getGoalWeightByUserId(userId: Long) : LiveData<Short>{
        return repository.getGoalWeightByUserId(userId)
    }

    fun setGoalWeightByUserId(userId: Long, goalWeight : Short){
        viewModelScope.launch {
            repository.setGoalWeightByUserId(userId, goalWeight)
        }
    }

    fun setUserLoggedIn(userId : Long, loggedIn : Boolean) :LiveData<Boolean>{
        val result = MutableLiveData<Boolean>()
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

    fun deleteAllTables() : LiveData<Boolean>{
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.postValue(repository.deleteAllTables())
        }
        return result
    }

}