package com.example.fitnessappclient.repository.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "SETS",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutExercise::class,
            parentColumns = arrayOf("workoutExerciseId"),
            childColumns = arrayOf("workoutExerciseId"),
            onDelete = ForeignKey.CASCADE
        )
        ,
        ForeignKey(
            entity = WorkoutPlanExercises::class,
            parentColumns = arrayOf("workoutPlanExerciseId"),
            childColumns = arrayOf("workoutPlanExerciseId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MySet(
    @PrimaryKey(autoGenerate = true)
    val setId: Long,
    val workoutExerciseId : Long?,
    val workoutPlanExerciseId : Long?,
    val nTh: Short,
    val numberOfRepetitions: Short,
    val secondaryNumber: Short
)