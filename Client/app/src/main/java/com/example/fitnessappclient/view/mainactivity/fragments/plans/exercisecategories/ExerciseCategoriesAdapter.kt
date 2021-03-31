package com.example.fitnessappclient.view.mainactivity.fragments.plans.exercisecategories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.ExerciseCategory
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.item_exercise.view.*

class ExerciseCategoriesAdapter : RecyclerView.Adapter<ExerciseCategoriesAdapter.CategoryViewHolder>() {

    var workoutCategories = emptyList<ExerciseCategory>()

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init{
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = workoutCategories[position].exerciseCategoryId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.itemView.apply{
            tv_exercise_name.text = workoutCategories[position].bodyPart
            tv_exercise_number.text = context.getString(R.string.which,position + 1)
            setOnClickListener{

            }
            (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 0
            if(workoutCategories.lastIndex == position){
                (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 1000
            }
        }
    }

    override fun getItemCount(): Int {
        return workoutCategories.size
    }

    fun setData(exerciseCategories: List<ExerciseCategory>){
        this.workoutCategories = exerciseCategories
        notifyDataSetChanged()
    }

    fun removeAt(position: Int, workoutViewModel: WorkoutViewModel){
        workoutViewModel.removeExerciseCategory(workoutCategories[position])
    }

}