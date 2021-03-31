package com.example.fitnessappclient.view.mainactivity.fragments.profile.measurement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.MeasuringSession
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.item_exercise.view.*
import java.text.SimpleDateFormat

class MeasurementHistoryAdapter(val userMeasurementAdapterInterface: UserMeasurementAdapterInterface) : RecyclerView.Adapter<MeasurementHistoryAdapter.MeasurementViewHolder>() {
    var measurements = emptyList<MeasuringSession>()

    inner class MeasurementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init{
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = measurements[position].sessionId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasurementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return MeasurementViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeasurementViewHolder, position: Int) {
        holder.itemView.apply{
            val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm")
            tv_exercise_name.text = sdf.format(measurements[position].sessionDate)
            tv_exercise_number.text = context.getString(R.string.which,position + 1)
            setOnClickListener{
                userMeasurementAdapterInterface.onClickListener(measurements[position])
            }
            (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 0
            if(measurements.lastIndex == position){
                (holder.itemView.layoutParams as RecyclerView.LayoutParams).bottomMargin = 1000
            }
        }
    }

    override fun getItemCount(): Int {
        return measurements.size
    }

    fun setData(measurements : List<MeasuringSession>){
        this.measurements = measurements
        notifyDataSetChanged()
    }

    fun removeAt(position: Int, workoutViewModel: WorkoutViewModel){
        workoutViewModel.removeMeasuringSession(measurements[position])
    }

}