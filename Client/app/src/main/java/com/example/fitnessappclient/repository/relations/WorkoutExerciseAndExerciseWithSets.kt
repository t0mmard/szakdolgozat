package com.example.fitnessappclient.repository.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fitnessappclient.repository.entities.Exercise
import com.example.fitnessappclient.repository.entities.MySet
import com.example.fitnessappclient.repository.entities.WorkoutExercise

data class WorkoutExerciseAndExerciseWithSets (

    @Embedded
    val workoutExercise: WorkoutExerciseAndExercise,
    @Relation(
        parentColumn = "setId",
        entityColumn = "setId"
    )
    val sets: List<MySet>

)