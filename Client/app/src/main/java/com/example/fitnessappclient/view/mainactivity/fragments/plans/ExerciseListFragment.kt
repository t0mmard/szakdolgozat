package com.example.fitnessappclient.view.mainactivity.fragments.plans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessappclient.R
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_exercise_list.view.*
import kotlinx.android.synthetic.main.fragment_workout_list.view.*

class ExerciseListFragment : Fragment() {

    private lateinit var workoutViewModel : WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_exercise_list, container, false)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        val adapter = ExerciseAdapter()
        view.rv_exercise_list.adapter = adapter
        view.rv_exercise_list.layoutManager = LinearLayoutManager(requireContext())

        workoutViewModel.getAllExercises().observe(viewLifecycleOwner, Observer { exercises ->
            adapter.setData(exercises)
            //if(exercises.isEmpty()) //view.tv_workoutlist_empty.visibility = View.VISIBLE
            //else                    //view.tv_workoutlist_empty.visibility = View.INVISIBLE
        })

        return view
    }
}