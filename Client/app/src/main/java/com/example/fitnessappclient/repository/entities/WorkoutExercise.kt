package com.example.fitnessappclient.repository.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "WORKOUT_EXERCISES",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = arrayOf("workoutId"),
            childColumns = arrayOf("workoutId"),
            onDelete = ForeignKey.CASCADE
        )
    ,
        ForeignKey(
            entity = Exercise::class,
            parentColumns = arrayOf("exerciseId"),
            childColumns = arrayOf("exerciseId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutExercise(
    @PrimaryKey(autoGenerate = true)
    val workoutExerciseId: Long,
    val workoutId: Long,
    val exerciseId: Long,
    val clientComment: String?,
    val coachComment: String?,
    val creatorId: Long
)