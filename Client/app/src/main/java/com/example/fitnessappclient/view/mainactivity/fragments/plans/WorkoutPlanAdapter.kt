package com.example.fitnessappclient.view.mainactivity.fragments.plans

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.Exercise
import com.example.fitnessappclient.repository.entities.WorkoutPlan
import kotlinx.android.synthetic.main.item_exercise.view.*

class WorkoutPlanAdapter : RecyclerView.Adapter<WorkoutPlanAdapter.WorkoutPlanViewHolder>() {

    var workoutPlans = emptyList<WorkoutPlan>()

    inner class WorkoutPlanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init{
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = workoutPlans[position].workoutPlanId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutPlanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return WorkoutPlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutPlanViewHolder, position: Int) {
        holder.itemView.apply{
            tv_exercise_name.text = workoutPlans[position].workoutplanName
            tv_exercise_number.text = context.getString(R.string.which,position + 1)
            setOnClickListener{
                //TODO
            }
            (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 0
            if(workoutPlans.lastIndex == position){
                (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 1000
            }
        }
    }

    override fun getItemCount(): Int {
        return workoutPlans.size
    }

    fun setData(workoutPlans : List<WorkoutPlan>){
        this.workoutPlans = workoutPlans
        notifyDataSetChanged()
    }

}