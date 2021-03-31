package com.example.fitnessappclient.view.mainactivity.fragments.workouts.workouthome

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.Workout
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.item_workout.view.*

class WorkoutAdapter( private val myListener: WorkoutRecyclerViewListener) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>(){

    private var workouts = emptyList<Workout>()

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init{
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = workouts[position].workoutId

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.itemView.apply{
            tv_workout_name.text = workouts[position].workoutName
            tv_workout_number.text = context.getString(R.string.which,position + 1)
            setOnClickListener{
                myListener.myOnClickListener(workouts[position])
            }

            (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 0
            if(workouts.lastIndex == position){
                (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 1000
            }
        }
    }

    fun setData(workouts: List<Workout>){
        this.workouts = workouts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return workouts.size
    }

    fun removeAt(position: Int, workoutViewModel: WorkoutViewModel){
        workoutViewModel.removeWorkout(workouts[position])
        notifyDataSetChanged()
    }

}