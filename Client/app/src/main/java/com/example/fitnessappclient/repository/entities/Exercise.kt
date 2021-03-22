package com.example.fitnessappclient.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "EXERCISES"
)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId:Long,
    val exerciseName:String,
    val exerciseType: ExerciseType
)

enum class ExerciseType {
    TIME,REPETITION,REPETITION_WITH_WEIGHT
}