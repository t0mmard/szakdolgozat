package com.example.fitnessappclient.view.mainactivity.fragments.workout_exercises

import android.view.MotionEvent
import android.view.View
import com.example.fitnessappclient.repository.entities.Exercise
import com.example.fitnessappclient.repository.entities.WorkoutExercise
import com.example.fitnessappclient.repository.relations.WorkoutExerciseAndExercise

interface WorkoutExerciseRecyclerViewListener {
    fun myOnClickListener(workoutExercise: WorkoutExerciseAndExercise)
    fun myOnTouchListener(view : View, motionevent : MotionEvent) : Boolean
}