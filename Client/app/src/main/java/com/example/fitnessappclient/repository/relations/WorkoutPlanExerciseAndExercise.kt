package com.example.fitnessappclient.repository.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fitnessappclient.repository.entities.Exercise
import com.example.fitnessappclient.repository.entities.WorkoutPlanExercises

data class WorkoutPlanExerciseAndExercise (
    @Embedded
    val workoutPlanExercise : WorkoutPlanExercises,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "exerciseId"
    )
    val exercise: Exercise
)