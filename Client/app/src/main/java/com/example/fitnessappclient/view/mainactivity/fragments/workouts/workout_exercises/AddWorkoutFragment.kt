package com.example.fitnessappclient.view.mainactivity.fragments.workouts.workout_exercises

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.Workout
import com.example.fitnessappclient.repository.entities.WorkoutExercise
import com.example.fitnessappclient.repository.entities.WorkoutPlan
import com.example.fitnessappclient.view.mainactivity.MainActivity
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_add_workout.*
import kotlinx.android.synthetic.main.fragment_add_workout.view.*

class AddWorkoutFragment : Fragment() {

    private lateinit var workoutViewModel : WorkoutViewModel
    private var workoutPlanList = emptyList<WorkoutPlan>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_workout, container, false)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        initButtons(view)

        return view
    }

    private fun initButtons(view : View){

        view.switch_addworkout_emptyworkout.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                view.rl_addworkout_spinnerlayout.visibility = View.INVISIBLE
            }
            else{
                view.rl_addworkout_spinnerlayout.visibility = View.VISIBLE
            }
        }

        view.btn_addworkout_addworkout.setOnClickListener{
            insertWorkout(view)
        }


        workoutViewModel.getAllWorkoutPlans().observe(viewLifecycleOwner, Observer { workoutPlans ->
            workoutPlanList = workoutPlans
            val workoutPlanNames = mutableListOf<String>()
            workoutPlans.forEach { workoutPlan -> workoutPlanNames.add(workoutPlan.workoutplanName) }
            val adapter = ArrayAdapter<String>( requireContext(), R.layout.support_simple_spinner_dropdown_item, workoutPlanNames)
            view.sp_addworkout_workoutplan.adapter = adapter

            view.sp_addworkout_workoutplan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, viewLocal: View?, position: Int, id: Long){

                }
            }
        })

    }

    private fun insertWorkout(view : View){
        var workoutName = et_addworkout_name.text.toString()

        val main = activity as MainActivity

        //Ha üres automatikusan nevet kap
        if(inputCheck(workoutName)){ workoutName = requireContext().resources.getString(R.string.new_workout_hint) }
        val workout = Workout(0L, main.currentUser, workoutName, java.sql.Date.valueOf(main.selectedDate.toString()),
            null,null,null, main.currentUser)

        val workoutIdLiveData = workoutViewModel.addWorkout(workout)
        btn_addworkout_addworkout.isEnabled = false
        workoutIdLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { workoutId ->
            Toast.makeText(requireContext(), "Sikeresen hozzáadva!", Toast.LENGTH_SHORT).show()

            //Load exercises from workoutplan
            if (!view.switch_addworkout_emptyworkout.isChecked && view.sp_addworkout_workoutplan.selectedItemPosition > -1) {
                workoutViewModel.getWorkoutExerciseWithWorkoutExercisesByWorkoutPlanId(
                    workoutPlanList[sp_addworkout_workoutplan.selectedItemPosition].workoutPlanId
                )
                    .observe(viewLifecycleOwner, Observer { item ->
                        item.workoutPlanExercises.forEach { workoutExercise ->
                            workoutViewModel.insertWorkoutExercise(
                                WorkoutExercise(
                                    0,
                                    workoutId,
                                    workoutExercise.exerciseId,
                                    null,
                                    null,
                                    main.currentUser
                                )
                            )
                        }

                        val action =
                            AddWorkoutFragmentDirections.actionAddWorkoutFragmentToExerciseListFragment(
                                workoutId
                            )
                        findNavController().navigate(action)
                    })
            }
            else{
            val action =
                AddWorkoutFragmentDirections.actionAddWorkoutFragmentToExerciseListFragment(
                    workoutId
                )
            findNavController().navigate(action)
            }
        })

    }

    private fun inputCheck(workoutName: String):Boolean{
        return TextUtils.isEmpty(workoutName)
    }

}