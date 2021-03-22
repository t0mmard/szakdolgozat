package com.example.fitnessappclient.view.mainactivity.fragments.workouts

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.Workout
import com.example.fitnessappclient.view.mainactivity.MainActivity
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_add_workout.*
import kotlinx.android.synthetic.main.fragment_add_workout.view.*
import java.util.*

class AddWorkoutFragment : Fragment() {

    private lateinit var workoutViewModel : WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_workout, container, false)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        view.btnAddExerciseType.setOnClickListener{
            insertWorkout()
        }

        return view
    }

    private fun insertWorkout(){
        val workoutName = etName.text.toString()

        val main = activity as MainActivity

        if(inputCheck(workoutName)){
            val workout = Workout(0, workoutViewModel.id,workoutName, java.sql.Date.valueOf(main.selectedDate.toString()),
                                null,null,null)
            val workoutIdLiveData = workoutViewModel.addWorkout(workout)
            btnAddExerciseType.isEnabled = false
            workoutIdLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer{ workoutId ->
                Toast.makeText(requireContext(),"Sikeresen hozzáadva!",Toast.LENGTH_SHORT).show()
                val action = AddWorkoutFragmentDirections.actionAddWorkoutFragmentToExerciseListFragment(workoutId)
                findNavController().navigate(action)
            })
            //
        }
        else{
            Toast.makeText(requireContext(),"Név megadása kötelező!",Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(workoutName: String):Boolean{
        return !TextUtils.isEmpty(workoutName)
    }

}