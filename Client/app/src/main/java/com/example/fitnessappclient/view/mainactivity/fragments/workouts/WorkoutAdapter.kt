package com.example.fitnessappclient.view.mainactivity.fragments.workouts

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
            tvWorkoutName.text = workouts[position].workoutName
            tvExercise.text = "csere"
            setOnClickListener{
                myListener.myOnClickListener(workouts[position])
            }
            setOnTouchListener(View.OnTouchListener{ view, motionevent ->
                myListener.myOnTouchListener(view,motionevent)
            })
            if(workouts.lastIndex == position){
                (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 1000
            }
        }
    }

    fun setData(workouts: List<Workout>){
        println("itt történik a furcsaság")
        this.workouts = workouts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return workouts.size
    }

    fun removeAt(position: Int, workoutViewModel: WorkoutViewModel){
        workoutViewModel.removeWorkout(workouts[position])
    }
}