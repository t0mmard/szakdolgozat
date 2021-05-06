package com.example.fitnessappclient.view.mainactivity.fragments.plans.workoutplan

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
import com.example.fitnessappclient.repository.entities.WorkoutPlanExercises
import com.example.fitnessappclient.view.mainactivity.MainActivity
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_choose_workout_plan_exercise_type.view.*

class ChooseWorkoutPlanExerciseTypeFragment : Fragment() {


    private lateinit var workoutViewModel: WorkoutViewModel

    private var exerciseList = emptyList<Exercise>()
    private var type = ExerciseType.REPETITION
    private val args by navArgs<ChooseWorkoutPlanExerciseTypeFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =
            inflater.inflate(R.layout.fragment_choose_workout_plan_exercise_type, container, false)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        workoutViewModel.getAllExercisesByWorkoutType(type)
            .observe(viewLifecycleOwner, Observer { exercises ->
                exerciseList = exercises
            })


        val typeList = listOf<String>("Ismétlések", "Ismétlések súllyal", "Idő")

        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            typeList
        )
        view.sp_chooseworkoutplan_exercisetype.adapter = adapter

        view.sp_chooseworkoutplan_exercisetype.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    viewLocal: View?,
                    position: Int,
                    id: Long
                ) {

                    val exerciseNames = mutableListOf<String>()

                    when (position) {
                        0 -> {
                            type = ExerciseType.REPETITION
                            workoutViewModel.getAllExercisesByWorkoutType(type)
                                .observe(viewLifecycleOwner, Observer { exercises ->
                                    exerciseList = exercises

                                    exerciseList.forEach { exercise -> exerciseNames.add(exercise.exerciseName) }

                                    val exerciseAdapter = ArrayAdapter<String>(
                                        requireContext(),
                                        R.layout.support_simple_spinner_dropdown_item,
                                        exerciseNames.toMutableList()
                                    )
                                    view.sp_chooseworkoutplan_exercise.adapter = exerciseAdapter
                                })
                        }
                        1 -> {
                            type = ExerciseType.REPETITION_WITH_WEIGHT
                            workoutViewModel.getAllExercisesByWorkoutType(type)
                                .observe(viewLifecycleOwner, Observer { exercises ->
                                    exerciseList = exercises
                                    exerciseList.forEach { exercise -> exerciseNames.add(exercise.exerciseName) }

                                    val exerciseAdapter = ArrayAdapter<String>(
                                        requireContext(),
                                        R.layout.support_simple_spinner_dropdown_item,
                                        exerciseNames.toMutableList()
                                    )
                                    view.sp_chooseworkoutplan_exercise.adapter = exerciseAdapter
                                })
                        }
                        2 -> {
                            type = ExerciseType.TIME
                            workoutViewModel.getAllExercisesByWorkoutType(type)
                                .observe(viewLifecycleOwner, Observer { exercises ->
                                    exerciseList = exercises
                                    exerciseList.forEach { exercise -> exerciseNames.add(exercise.exerciseName) }

                                    val exerciseAdapter = ArrayAdapter<String>(
                                        requireContext(),
                                        R.layout.support_simple_spinner_dropdown_item,
                                        exerciseNames.toMutableList()
                                    )
                                    view.sp_chooseworkoutplan_exercise.adapter = exerciseAdapter
                                })
                        }
                    }
                }
            }

        view.btn_chooseworkoutplan_addexercise.setOnClickListener {
            if(view.sp_chooseworkoutplan_exercise.selectedItemPosition != -1) {
                val parentActivity = activity as MainActivity
                when (type) {
                    ExerciseType.REPETITION -> {
                        val workoutPlanExercise = WorkoutPlanExercises(
                            0,
                            args.workoutPlanId,
                            exerciseList[view.sp_chooseworkoutplan_exercise.selectedItemPosition].exerciseId,
                            parentActivity.currentUser
                        )
                        workoutViewModel.insertWorkoutPlanExercise(workoutPlanExercise)
                            .observe(viewLifecycleOwner,
                                Observer { newId ->
                                    val action =
                                        ChooseWorkoutPlanExerciseTypeFragmentDirections.actionChooseWorkoutPlanExerciseTypeFragmentToAddExerciseToWorkoutplanFragment(
                                            args.workoutPlanId
                                        )
                                    findNavController().navigate(action)
                                })
                    }
                    ExerciseType.REPETITION_WITH_WEIGHT -> {
                        val workoutPlanExercise = WorkoutPlanExercises(
                            0,
                            args.workoutPlanId,
                            exerciseList[view.sp_chooseworkoutplan_exercise.selectedItemPosition].exerciseId,
                            parentActivity.currentUser
                        )
                        workoutViewModel.insertWorkoutPlanExercise(workoutPlanExercise)
                            .observe(viewLifecycleOwner,
                                Observer { newId ->
                                    val action =
                                        ChooseWorkoutPlanExerciseTypeFragmentDirections.actionChooseWorkoutPlanExerciseTypeFragmentToAddExerciseToWorkoutplanFragment(
                                            args.workoutPlanId
                                        )
                                    findNavController().navigate(action)
                                })
                    }
                    ExerciseType.TIME -> {
                        val workoutPlanExercise = WorkoutPlanExercises(
                            0,
                            args.workoutPlanId,
                            exerciseList[view.sp_chooseworkoutplan_exercise.selectedItemPosition].exerciseId,
                            parentActivity.currentUser
                        )
                        workoutViewModel.insertWorkoutPlanExercise(workoutPlanExercise)
                            .observe(viewLifecycleOwner,
                                Observer { newId ->
                                    val action =
                                        ChooseWorkoutPlanExerciseTypeFragmentDirections.actionChooseWorkoutPlanExerciseTypeFragmentToAddExerciseToWorkoutplanFragment(
                                            args.workoutPlanId
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