package com.example.fitnessappclient.view.mainactivity.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitnessappclient.R
import kotlinx.android.synthetic.main.fragment_not_logged_in_tile.view.*

class NotLoggedInTileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_not_logged_in_tile, container, false)

        initButtons(view)

        return view
    }

    private fun initButtons(view: View){
        view.btn_notloggedin_login.setOnClickListener {
            (parentFragment as ProfileFragment).navigateFromProfileToLoginScreen()
        }

        view.btn_notloggedin_register.setOnClickListener {
            (parentFragment as ProfileFragment).navigateFromProfileToRegisterScreen()
        }
    }

}