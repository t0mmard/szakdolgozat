package com.example.fitnessappclient.repository.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fitnessappclient.repository.entities.Exercise
import com.example.fitnessappclient.repository.entities.WorkoutExercise

data class WorkoutExerciseAndExercise (
    @Embedded val workoutExercise: WorkoutExercise,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "exerciseId"
    )
    val exercise: Exercise
)