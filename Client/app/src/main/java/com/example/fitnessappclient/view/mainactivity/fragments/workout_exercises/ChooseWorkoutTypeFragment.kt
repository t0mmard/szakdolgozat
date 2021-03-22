package com.example.fitnessappclient.view.mainactivity.fragments.workout_exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.NavType
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.Exercise
import com.example.fitnessappclient.repository.entities.ExerciseType
import com.example.fitnessappclient.repository.entities.WorkoutExercise
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_add_workout.view.*
import kotlinx.android.synthetic.main.fragment_choose_workout_type.*
import kotlinx.android.synthetic.main.fragment_choose_workout_type.view.*
import kotlinx.android.synthetic.main.fragment_choose_workout_type.view.btnAddExerciseType

class ChooseWorkoutTypeFragment : Fragment() {

    private lateinit var workoutViewModel: WorkoutViewModel

    private var exerciseList = emptyList<Exercise>()
    private var type = ExerciseType.REPETITION
    private val args by navArgs<ChooseWorkoutTypeFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_choose_workout_type, container, false)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        workoutViewModel.getAllExercisesByWorkoutType(type).observe(viewLifecycleOwner, Observer { exercises ->
            exerciseList = exercises
        })


        val typeList = listOf<String>("Ismétlések","Ismétlések súllyal","Idő")

        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            typeList
        )
        view.spExerciseType.adapter = adapter

        view.spExerciseType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, viewLocal: View?, position: Int, id: Long) {

                val exerciseNames = mutableListOf<String>()

                when(position){
                    0 ->{
                        type = ExerciseType.REPETITION
                        workoutViewModel.getAllExercisesByWorkoutType(type).observe(viewLifecycleOwner, Observer { exercises ->
                            exerciseList = exercises

                            exerciseList.forEach { exercise -> exerciseNames.add(exercise.exerciseName) }

                            val exerciseAdapter = ArrayAdapter<String>(
                                requireContext(),
                                R.layout.support_simple_spinner_dropdown_item,
                                exerciseNames.toMutableList()
                            )
                            view.spExercise.adapter = exerciseAdapter
                        })
                    }
                    1 ->{
                        type = ExerciseType.REPETITION_WITH_WEIGHT
                        workoutViewModel.getAllExercisesByWorkoutType(type).observe(viewLifecycleOwner, Observer { exercises ->
                            exerciseList = exercises
                            exerciseList.forEach { exercise -> exerciseNames.add(exercise.exerciseName) }

                            val exerciseAdapter = ArrayAdapter<String>(
                                requireContext(),
                                R.layout.support_simple_spinner_dropdown_item,
                                exerciseNames.toMutableList()
                            )
                            view.spExercise.adapter = exerciseAdapter
                        })
                    }
                    2 ->{
                        type = ExerciseType.TIME
                        workoutViewModel.getAllExercisesByWorkoutType(type).observe(viewLifecycleOwner, Observer { exercises ->
                            exerciseList = exercises
                            exerciseList.forEach { exercise -> exerciseNames.add(exercise.exerciseName) }

                            val exerciseAdapter = ArrayAdapter<String>(
                                requireContext(),
                                R.layout.support_simple_spinner_dropdown_item,
                                exerciseNames.toMutableList()
                            )
                            view.spExercise.adapter = exerciseAdapter
                        })
                    }
                }
            }
        }

        view.btnAddExerciseType.setOnClickListener {
            when(type){
                ExerciseType.REPETITION -> {
                    val workoutExercise = WorkoutExercise(
                        0,
                        args.workoutId,
                        exerciseList[spExercise.selectedItemPosition].exerciseId,
                        null,
                        null
                    )
                    workoutViewModel.insertWorkoutExercise(workoutExercise).observe(viewLifecycleOwner,
                        Observer { newId ->
                            val action = ChooseWorkoutTypeFragmentDirections.actionChooseWorkoutTypeFragmentToAddRepsWorkoutExerciseFragment(args.workoutId,newId,"")
                            findNavController().navigate(action)
                        })
                }
                ExerciseType.REPETITION_WITH_WEIGHT -> {
                    val workoutExercise = WorkoutExercise(
                        0,
                        args.workoutId,
                        exerciseList[spExercise.selectedItemPosition].exerciseId,
                        null,
                        null
                    )
                    workoutViewModel.insertWorkoutExercise(workoutExercise).observe(viewLifecycleOwner,
                        Observer { newId ->
                            val action = ChooseWorkoutTypeFragmentDirections.actionChooseWorkoutTypeFragmentToAddWeightRepsWorkoutExerciseFragment(args.workoutId,newId,"")
                            findNavController().navigate(action)
                        })
                }
                ExerciseType.TIME -> {
                    val workoutExercise = WorkoutExercise(
                        0,
                        args.workoutId,
                        exerciseList[spExercise.selectedItemPosition].exerciseId,
                        null,
                        null
                    )
                    workoutViewModel.insertWorkoutExercise(workoutExercise).observe(viewLifecycleOwner,
                        Observer { newId ->
                            val action = ChooseWorkoutTypeFragmentDirections.actionChooseWorkoutTypeFragmentToAddTimeRepsWorkoutExerciseFragment(args.workoutId,newId,"")
                            findNavController().navigate(action)
                        })
                }
            }
        }

        return view
    }

}