package com.example.fitnessappclient.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "USERS"
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId : Long,
    val email : String,
    val lastName: String,
    val passwordHash: String?,
    val passwordSaved: Boolean,
    val loggedIn: Boolean,
    val synchronizationDate: Date
)