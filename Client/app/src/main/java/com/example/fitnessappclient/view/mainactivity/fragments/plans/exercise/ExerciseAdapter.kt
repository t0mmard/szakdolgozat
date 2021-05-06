package com.example.fitnessappclient.view.mainactivity.fragments.plans.exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.Exercise
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.item_exercise.view.*

class ExerciseAdapter : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    var exercises = emptyList<Exercise>()

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init{
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = exercises[position].exerciseId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.itemView.apply{
            println(position)
            tv_exercise_name.text = exercises[position].exerciseName
            tv_exercise_number.text = context.getString(R.string.which,position + 1)
            (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 0
            if(itemCount-1 == position){
                (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 1000
            }
        }
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun setData(exercises : List<Exercise>){
        this.exercises = exercises
        notifyDataSetChanged()
    }

    fun removeAt(position: Int, workoutViewModel: WorkoutViewModel){
        workoutViewModel.removeExercise(exercises[position])
    }

}