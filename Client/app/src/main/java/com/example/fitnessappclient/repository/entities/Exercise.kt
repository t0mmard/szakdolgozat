package com.example.fitnessappclient.repository.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "EXERCISES",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseCategory::class,
            parentColumns = arrayOf("exerciseCategoryId"),
            childColumns = arrayOf("exerciseCategoryId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId:Long,
    val exerciseName:String,
    val exerciseCategoryId: Long,
    val exerciseType: ExerciseType,
    val deletableByUser: Boolean,
    val creatorId: Long
    //val workoutCategoryId: Long TODO: Ezt is fel kell venni
)

enum class ExerciseType {
    TIME,REPETITION,REPETITION_WITH_WEIGHT
}