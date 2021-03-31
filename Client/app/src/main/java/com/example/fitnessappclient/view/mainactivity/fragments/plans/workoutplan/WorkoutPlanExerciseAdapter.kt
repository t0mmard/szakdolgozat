package com.example.fitnessappclient.view.mainactivity.fragments.plans.workoutplan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.relations.WorkoutPlanExerciseAndExercise
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.item_exercise.view.*

class WorkoutPlanExerciseAdapter : RecyclerView.Adapter<WorkoutPlanExerciseAdapter.WorkoutPlanExerciseViewHolder>() {

    var exercises = emptyList<WorkoutPlanExerciseAndExercise>()

    inner class WorkoutPlanExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init{
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = exercises[position].workoutPlanExercise.workoutPlanExerciseId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutPlanExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return WorkoutPlanExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutPlanExerciseViewHolder, position: Int) {
        holder.itemView.apply{
            tv_exercise_name.text = exercises[position].exercise.exerciseName
            tv_exercise_number.text = context.getString(R.string.which,position + 1)
            setOnClickListener{
                //TODO
            }
            (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 0
            if(itemCount-1 == position){
                println("beléptem az utolsóra $position")
                (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 1000
            }
        }
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun setData(exercises : List<WorkoutPlanExerciseAndExercise>){
        this.exercises = exercises
        notifyDataSetChanged()
    }

    fun removeAt(position: Int, workoutViewModel: WorkoutViewModel){
        workoutViewModel.removeWorkoutPlanExercise(exercises[position].workoutPlanExercise)
    }

}