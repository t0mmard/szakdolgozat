package com.example.fitnessappclient.view.mainactivity.fragments.workout_exercises

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.ExerciseType
import com.example.fitnessappclient.repository.entities.MySet
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.item_set.view.*

class SetsAdapter(val exerciseType: ExerciseType): RecyclerView.Adapter<SetsAdapter.SetViewHolder>() {

    var sets = emptyList<MySet>()

    inner class SetViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    init{
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = sets[position].setId


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_set, parent, false)
        return SetViewHolder(view)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.itemView.apply {
            when (exerciseType) {
                ExerciseType.REPETITION -> {
                    //csak 1 érték van ezért a constrainteket kicsit átalakaítjuk
                    val set = ConstraintSet()
                    set.clone(cl_set_inner)
                    set.clear(R.id.tv_set1,ConstraintSet.START)
                    set.clear(R.id.tv_set1,ConstraintSet.END)
                    set.addToHorizontalChain(R.id.tv_set1,ConstraintSet.PARENT_ID,ConstraintSet.PARENT_ID)
                    set.applyTo(cl_set_inner)

                    tv_set1.text = "${sets[position].numberOfRepetitions}"
                    tv_set_unit1.text = context.getString(R.string.repetition)
                    tv_set2.visibility = View.INVISIBLE
                    tv_set_unit2.visibility = View.INVISIBLE
                }
                ExerciseType.REPETITION_WITH_WEIGHT -> {
                    tv_set1.text = "${sets[position].numberOfRepetitions}"
                    tv_set_unit1.text = context.getString(R.string.repetition)
                    tv_set2.text = "${sets[position].secondaryNumber}"
                    tv_set_unit2.text = context.getString(R.string.weight)
                }
                ExerciseType.TIME -> {
                    tv_set1.text = "${sets[position].numberOfRepetitions}"
                    tv_set_unit1.text = context.getString(R.string.time)
                    tv_set2.text = "${sets[position].secondaryNumber}"
                    tv_set_unit2.text = context.getString(R.string.distance)
                }
            }
            tv_set_number.text = context.getString(R.string.which, (position + 1))
        }
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