package com.example.fitnessappclient.view.mainactivity.fragments.plans.workoutplan

import com.example.fitnessappclient.repository.entities.WorkoutPlan

interface WorkoutPlanAdapterInterface  {
    fun onClick(workoutPlan : WorkoutPlan)
}