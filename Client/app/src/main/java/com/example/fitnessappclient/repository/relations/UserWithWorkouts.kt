package com.example.fitnessappclient.repository.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fitnessappclient.repository.entities.User
import com.example.fitnessappclient.repository.entities.Workout

//1 to n 1user nworkout

data class UserWithWorkouts (
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val workouts: List<Workout>
)