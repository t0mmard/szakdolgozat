package com.example.fitnessappclient.view.mainactivity.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.fitnessappclient.*
import com.example.fitnessappclient.view.mainactivity.MainActivity
import com.example.fitnessappclient.view.mainactivity.fragments.profile.coach.CoachTileFragment
import com.example.fitnessappclient.view.mainactivity.fragments.profile.login.LoggedInTileFragment
import com.example.fitnessappclient.view.mainactivity.fragments.profile.login.NotLoggedInTileFragment
import com.example.fitnessappclient.view.mainactivity.fragments.profile.measurement.MeasurementsTileFragment
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_workout_list.view.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)


        initButtons(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.commit{
            val parentActivity = activity as MainActivity

            if(parentActivity.isUserLoggedIn) {
                replace(R.id.fragment_profile_login, LoggedInTileFragment())
            }

            else{
                replace(R.id.fragment_profile_login, NotLoggedInTileFragment())
            }

            replace(R.id.fragment_profile_measurements, MeasurementsTileFragment())
            replace(R.id.fragment_profile_coach, CoachTileFragment())
        }
    }

    private fun initButtons(view: View){

        view.bnv_profile_navmenu.menu.getItem(1).isChecked = true

        //reset to default selected item after leaving
        view.bnv_profile_navmenu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home ->{
                    it.isCheckable = false
                    view.bnv_profile_navmenu.selectedItemId = R.id.profile
                    findNavController().navigate(R.id.action_profileFragment_to_workoutListFragment)
                }
                R.id.plan ->{
                    it.isChecked = false
                    view.bnv_profile_navmenu.selectedItemId = R.id.profile
                    findNavController().navigate(R.id.action_profileFragment_to_planFragment)
                }
                R.id.profile -> {
                    it.isCheckable = true
                    return@setOnNavigationItemSelectedListener true
                }
                else ->{}
            }
            false
        }

    }

    public fun navigateFromProfileToLoginScreen(){
        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

    public fun navigateFromProfileToRegisterScreen(){
        findNavController().navigate(R.id.action_profileFragment_to_registerFragment)
    }

}