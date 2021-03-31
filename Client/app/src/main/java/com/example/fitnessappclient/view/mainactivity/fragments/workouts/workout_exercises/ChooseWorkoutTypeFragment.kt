package com.example.fitnessappclient.view.mainactivity.fragments.workouts.workout_exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.Exercise
import com.example.fitnessappclient.repository.entities.ExerciseType
import com.example.fitnessappclient.repository.entities.WorkoutExercise
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_choose_workout_type.*
import kotlinx.android.synthetic.main.fragment_choose_workout_type.view.*

class ChooseWorkoutTypeFragment : Fragment() {

    private lateinit var workoutViewModel: WorkoutViewModel

    private var exerciseList = emptyList<Exercise>()
    private var type = ExerciseType.REPETITION
    private val args by navArgs<ChooseWorkoutTypeFragmentArgs>()

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

        val adapter = ArrayAdapter<String>( requireContext(), R.layout.support_simple_spinner_dropdown_item, typeList )
        view.sp_chooseworkout_exercisetype.adapter = adapter

        view.sp_chooseworkout_exercisetype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
                            view.sp_chooseworkout_exercise.adapter = exerciseAdapter
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
                            view.sp_chooseworkout_exercise.adapter = exerciseAdapter
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
                            view.sp_chooseworkout_exercise.adapter = exerciseAdapter
                        })
                    }
                }
            }
        }

        view.btn_chooseworkout_addexercise.setOnClickListener {
            if(sp_chooseworkout_exercise.selectedItemPosition != -1) {
                when (type) {
                    ExerciseType.REPETITION -> {
                        val workoutExercise = WorkoutExercise(
                            0,
                            args.workoutId,
                            exerciseList[sp_chooseworkout_exercise.selectedItemPosition].exerciseId,
                            null,
                            null
                        )
                        workoutViewModel.insertWorkoutExercise(workoutExercise)
                            .observe(viewLifecycleOwner,
                                Observer { newId ->
                                    val action =
                                        ChooseWorkoutTypeFragmentDirections.actionChooseWorkoutTypeFragmentToAddRepsWorkoutExerciseFragment(
                                            args.workoutId,
                                            newId,
                                            ""
                                        )
                                    findNavController().navigate(action)
                                })
                    }
                    ExerciseType.REPETITION_WITH_WEIGHT -> {
                        val workoutExercise = WorkoutExercise(
                            0,
                            args.workoutId,
                            exerciseList[sp_chooseworkout_exercise.selectedItemPosition].exerciseId,
                            null,
                            null
                        )
                        workoutViewModel.insertWorkoutExercise(workoutExercise)
                            .observe(viewLifecycleOwner,
                                Observer { newId ->
                                    val action =
                                        ChooseWorkoutTypeFragmentDirections.actionChooseWorkoutTypeFragmentToAddWeightRepsWorkoutExerciseFragment(
                                            args.workoutId,
                                            newId,
                                            ""
                                        )
                                    findNavController().navigate(action)
                                })
                    }
                    ExerciseType.TIME -> {
                        val workoutExercise = WorkoutExercise(
                            0,
                            args.workoutId,
                            exerciseList[sp_chooseworkout_exercise.selectedItemPosition].exerciseId,
                            null,
                            null
                        )
                        workoutViewModel.insertWorkoutExercise(workoutExercise)
                            .observe(viewLifecycleOwner,
                                Observer { newId ->
                                    val action =
                                        ChooseWorkoutTypeFragmentDirections.actionChooseWorkoutTypeFragmentToAddTimeRepsWorkoutExerciseFragment(
                                            args.workoutId,
                                            newId,
                                            ""
                                        )
                                    findNavController().navigate(action)
                                })
                    }
                }
            }
        }

        return view
    }

}