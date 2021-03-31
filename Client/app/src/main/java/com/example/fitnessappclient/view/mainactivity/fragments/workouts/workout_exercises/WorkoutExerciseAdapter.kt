package com.example.fitnessappclient.view.mainactivity.fragments.workouts.workout_exercises

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.relations.WorkoutExerciseAndExercise
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.item_exercise.view.*

class WorkoutExerciseAdapter(private val myListener: WorkoutExerciseRecyclerViewListener) : RecyclerView.Adapter<WorkoutExerciseAdapter.WorkoutExerciseViewHolder>() {

    private var workoutExercises = emptyList<WorkoutExerciseAndExercise>()

    inner class WorkoutExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init{
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = workoutExercises[position].workoutExercise.workoutExerciseId

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return WorkoutExerciseViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: WorkoutExerciseViewHolder, position: Int) {
        holder.itemView.apply{
            setOnClickListener{
                myListener.myOnClickListener(workoutExercises[position])
            }
            if(workoutExercises.lastIndex == position){
                (layoutParams as RecyclerView.LayoutParams).bottomMargin = 1000
            }
            tv_exercise_number.text = context.getString(R.string.which, position + 1)
            tv_exercise_name.text = workoutExercises[position].exercise.exerciseName

            (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 0
            if(itemCount-1 == position){
                (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 1000
            }
        }
    }

    fun setData(workoutExercises : List<WorkoutExerciseAndExercise>){
        this.workoutExercises = workoutExercises
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return workoutExercises.size
    }

    fun removeAt(position: Int, workoutViewModel: WorkoutViewModel){
        workoutViewModel.removeWorkoutExercise(workoutExercises[position].workoutExercise)
    }

}