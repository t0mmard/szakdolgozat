package com.example.fitnessappclient.view.mainactivity.fragments.profile.measurement

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.MeasuringSession
import com.example.fitnessappclient.view.mainactivity.MainActivity
import com.example.fitnessappclient.view.mainactivity.fragments.profile.ProfileFragment
import com.example.fitnessappclient.view.mainactivity.fragments.profile.ProfileFragmentDirections
import com.example.fitnessappclient.viewmodel.UserViewModel
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.dialog_add_goal.view.*
import kotlinx.android.synthetic.main.dialog_add_measurementsession.view.*
import kotlinx.android.synthetic.main.fragment_measurements_tile.view.*
import java.util.*

class MeasurementsTileFragment : Fragment() {

    lateinit var workoutViewModel : WorkoutViewModel
    lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_measurements_tile, container, false)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        initButtons(view)

        initText(view)

        return view
    }

    private fun initText(view: View){
        val parentActivity = activity as MainActivity
        workoutViewModel.getMostRecentMeasuringSession().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it != null) {
                view.tv_measurements_weight.setText(
                    parentActivity.getString(
                        R.string.user_weight,
                        it.weight.toInt()
                    )
                )
            }
            else{
                view.tv_measurements_weight.setText(
                    parentActivity.getString(
                        R.string.user_weight,
                        0
                    )
                )
            }
        })
        userViewModel.getGoalWeightByUserId(parentActivity.currentUser).observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if(it != null) {
                    view.tv_measurements_weight_goal.setText(
                        parentActivity.getString(
                            R.string.user_weight_goal,
                            it
                        )
                    )
                }
                else{
                    view.tv_measurements_weight_goal.setText(
                        parentActivity.getString(
                            R.string.user_weight_goal,
                            0
                        )
                    )
                }
            })
    }

    private fun initButtons(view : View){
        view.btn_measurements_addsession.setOnClickListener {
           addNewMeasurementDialog()
        }

        view.btn_measurements_oldsession.setOnClickListener {
            val parentFragment = parentFragment as ProfileFragment
            parentFragment.findNavController().navigate(R.id.action_profileFragment_to_measurementHistoryFragment)
        }

        view.btn_set_weight_goal.setOnClickListener {
            setWorkoutWeightDialog()
        }

    }

    private fun setWorkoutWeightDialog(){
        val context = requireContext()
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_goal, null)
        dialogBuilder.setView(dialogView)

        dialogBuilder.setTitle(R.string.new_weight_goal)

        dialogBuilder.setPositiveButton(R.string.add,
            DialogInterface.OnClickListener { dialog, id ->
                val parentActivity = activity as MainActivity
                val goalWeight = dialogView.et_goal_weight.text.toString()
                var goalWeightShort : Short = 0
                if(goalWeight.isNotEmpty()) goalWeightShort = goalWeight.toShort()
                userViewModel.setGoalWeightByUserId(parentActivity.currentUser, goalWeightShort)
            }
        )

        dialogBuilder.setNegativeButton(R.string.cancel,
            DialogInterface.OnClickListener { dialog, id -> })

        dialogBuilder.create().show()

    }

    private fun addNewMeasurementDialog() {

        val context = requireContext()
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_measurementsession, null)
        dialogBuilder.setView(dialogView)

        dialogBuilder.setTitle(R.string.new_measuringsession_title)

        dialogBuilder.setPositiveButton(R.string.add,
            DialogInterface.OnClickListener { dialog, id ->
                val weight = dialogView.et_weight.text.toString()
                var weightShort : Short = 0
                if(weight.isNotEmpty()) weightShort = weight.toShort()

                val parentActivity = activity as MainActivity

                workoutViewModel.insertMeasuringSession(
                    MeasuringSession(
                        0,
                        parentActivity.currentUser,
                        weightShort,
                        Date()
                    )
                ).observe(viewLifecycleOwner, androidx.lifecycle.Observer { sessionId ->
                    val parentFragment = parentFragment as ProfileFragment
                    val weight = dialogView.et_weight.text.toString().toInt()
                    val action =
                        ProfileFragmentDirections.actionProfileFragmentToMeasurementSessionFragment(
                            sessionId,
                            weight
                        )
                    parentFragment.findNavController().navigate(action)
                })
            })

        dialogBuilder.setNegativeButton(R.string.cancel,
            DialogInterface.OnClickListener { dialog, id -> })

        dialogBuilder.create().show()
    }


}