package com.example.fitnessappclient.repository.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fitnessappclient.repository.entities.WorkoutPlan
import com.example.fitnessappclient.repository.entities.WorkoutPlanExercises

data class WorkoutPlanWithWorkoutPlanExercises (
    @Embedded
    val workoutPlan : WorkoutPlan,
    @Relation(
        parentColumn = "workoutPlanId",
        entityColumn = "workoutPlanId"
    )
    val workoutPlanExercises: List<WorkoutPlanExercises>
)