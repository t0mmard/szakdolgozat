package com.example.fitnessappclient.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
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
}