package com.example.fitnessappclient.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "WORKOUT_CATEGORIES"
)
data class ExerciseCategory (
    @PrimaryKey(autoGenerate = true)
    val exerciseCategoryId: Long,
    val bodyPart: String,
    val creatorId: Long,
    val deletableByUser: Boolean
)