package com.example.fitnessappclient.view.mainactivity.fragments.profile.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnessappclient.R
import com.example.fitnessappclient.view.mainactivity.MainActivity
import com.example.fitnessappclient.view.mainactivity.fragments.profile.ProfileFragment
import com.example.fitnessappclient.viewmodel.UserViewModel
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_logged_in_tile.view.*
import java.lang.Thread.sleep

class LoggedInTileFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_logged_in_tile, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        initButtons(view)

        return view
    }

    private fun initButtons(view: View){

        val parentActivity = activity as MainActivity

        view.btn_loggedin_logout.setOnClickListener {
            logout(view)
        }

        userViewModel.getUserByUserId(parentActivity.currentUser).observe(viewLifecycleOwner,
            Observer { user ->
                view.tv_loggedin_user.text = user.email
            })
    }

    private fun logout(view: View){
        val parentActivity = activity as MainActivity
        parentActivity.isUserLoggedIn = false
        userViewModel.setUserLoggedIn(parentActivity.currentUser, false)
        parentActivity.currentUser = -2L
        userViewModel.deleteAllTables().observe(viewLifecycleOwner, Observer {
            val parentFragment = parentFragment as ProfileFragment
            parentFragment.childFragmentManager.commit {
                replace(R.id.fragment_profile_login, NotLoggedInTileFragment())
            }
        })
    }

}