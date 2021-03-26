package com.example.fitnessappclient.view.mainactivity.fragments.plans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fitnessappclient.R
import kotlinx.android.synthetic.main.fragment_plan.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_workout_list.view.*

class PlanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_plan, container, false)

        initButtons(view)

        return view
    }

    private fun initButtons(view: View){

        initNavMenu(view)

        view.btn_plan_addworkoutplan.setOnClickListener {
            findNavController().navigate(R.id.action_planFragment_to_workoutPlanFragment)
        }

        view.btn_plan_addexercise.setOnClickListener {
            findNavController().navigate(R.id.action_planFragment_to_exerciseListFragment)
        }

        view.btn_plan_addmeasurement.setOnClickListener {
            findNavController().navigate(R.id.action_planFragment_to_measurementListFragment)
        }

    }

    private fun initNavMenu(view : View){
        view.bnv_plan_navmenu.menu.getItem(2).isChecked = true

        //reset to default selected item after leaving
        view.bnv_plan_navmenu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home ->{
                    it.isCheckable = false
                    view.bnv_plan_navmenu.selectedItemId = R.id.plan
                    findNavController().navigate(R.id.action_planFragment_to_workoutListFragment)
                }
                R.id.plan ->{
                    it.isCheckable = true
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    it.isChecked = false
                    view.bnv_plan_navmenu.selectedItemId = R.id.plan
                    findNavController().navigate(R.id.action_planFragment_to_profileFragment)
                }
                else ->{}
            }
            false
        }
    }
}