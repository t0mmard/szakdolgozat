package com.example.fitnessappclient.view.mainactivity.fragments.workouts.workouthome

import android.view.MotionEvent
import android.view.View
import com.example.fitnessappclient.repository.entities.Workout

interface WorkoutRecyclerViewListener {
    fun myOnClickListener(workout: Workout)
    fun myOnTouchListener(view : View, motionevent : MotionEvent) : Boolean
}