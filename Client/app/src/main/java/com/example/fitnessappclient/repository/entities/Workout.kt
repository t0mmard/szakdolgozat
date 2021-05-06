package com.example.fitnessappclient.repository.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "WORKOUTS",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("userId"),
            childColumns = arrayOf("userId"),
            onDelete = ForeignKey.CASCADE
        )
    ,
        ForeignKey(
            entity = WorkoutPlan::class,
            parentColumns = arrayOf("workoutPlanId"),
            childColumns = arrayOf("workoutPlanId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Workout (
    @PrimaryKey(autoGenerate = true)
    val workoutId: Long,
    val userId: Long,
    val workoutName: String,
    val workoutDate : Date,
    val clientComment: String?,
    val coachComment: String?,
    val workoutPlanId: Long?,
    val creatorId: Long
)