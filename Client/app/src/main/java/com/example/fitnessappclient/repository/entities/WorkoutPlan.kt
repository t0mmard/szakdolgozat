package com.example.fitnessappclient.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WORKOUT_PLANS")
data class WorkoutPlan (
    @PrimaryKey(autoGenerate = true)
    val workoutPlanId : Long,
    val workoutplanName: String,
    val workoutPlanDescripton: String,
    val modifiable: Boolean,
    val creatorId: Long
)