package com.example.fitnessappclient.repository.retrofit

import com.example.fitnessappclient.repository.entities.*

data class FullSynchronizationData(
    val exercises : List<Exercise>?,
    val exerciseCategories: List<ExerciseCategory>?,
    val measurements: List<Measurement>?,
    val measuringSessions: List<MeasuringSession>?,
    val mySets: List<MySet>?,
    val user: User?,
    val userMeasurements: List<UserMeasurement>?,
    val workouts: List<Workout>?,
    val workoutExercises: List<WorkoutExercise>?,
    val workoutPlans: List<WorkoutPlan>?,
    val workoutPlanExercises: List<WorkoutPlanExercises>?
)