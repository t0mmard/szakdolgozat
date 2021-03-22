package com.example.fitnessappclient.view.mainactivity.fragments.workout_exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.ExerciseType
import com.example.fitnessappclient.repository.entities.MySet
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.item_set.view.*

class SetsAdapter(val exerciseType: ExerciseType): RecyclerView.Adapter<SetsAdapter.SetViewHolder>() {

    var sets = emptyList<MySet>()

    inner class SetViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_set, parent, false)
        return SetViewHolder(view)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val view = holder.itemView
        when(exerciseType){
            ExerciseType.REPETITION->{
                view.tvSets.text = "${sets[position].numberOfRepetitions}"
            }
            ExerciseType.REPETITION_WITH_WEIGHT->{
                view.tvSets.text = "${sets[position].numberOfRepetitions} x ${sets[position].secondaryNumber}kg"
            }
            ExerciseType.TIME->{
                view.tvSets.text = "${sets[position].numberOfRepetitions}min.  ${sets[position].secondaryNumber}m"
            }
        }
        view.tvSetNumber.text = (position + 1).toString() + "."
    }

    override fun getItemCount(): Int {
       return  sets.size
    }

    fun setData(sets : List<MySet>){
        this.sets = sets
        notifyDataSetChanged()
    }

    fun removeAt(position: Int, workoutViewModel: WorkoutViewModel){
        workoutViewModel.removeSet(sets[position])
    }
}