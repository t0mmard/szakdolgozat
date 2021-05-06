package com.example.fitnessappclient.repository.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "WORKOUT_PLAN_EXERCISES",
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = arrayOf("exerciseId"),
            childColumns = arrayOf("exerciseId"),
            onDelete = ForeignKey.CASCADE
        )
        ,
        ForeignKey(
            entity = WorkoutPlan::class,
            parentColumns = arrayOf("workoutPlanId"),
            childColumns = arrayOf("workoutPlanId"),
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class WorkoutPlanExercises(
    @PrimaryKey(autoGenerate = true)
    val workoutPlanExerciseId: Long,
    val workoutPlanId: Long,
    val exerciseId: Long,
    val creatorId: Long
)