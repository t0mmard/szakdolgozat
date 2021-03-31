package com.example.fitnessappclient.view.mainactivity.fragments.profile.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitnessappclient.R
import com.example.fitnessappclient.view.mainactivity.MainActivity
import com.example.fitnessappclient.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        initButtons(view)

        return view
    }

    private fun initButtons(view: View){
        view.btn_login_login.setOnClickListener {
            login(view)
        }

        view.btn_login_toregister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun login(view : View){
        val userName = view.et_login_username.text.toString()
        val password = view.et_login_password.text.toString()
        println(userName)
        println(password)
        if(checkEmailAndPassword(userName, password))
        if(userViewModel.isLoginSuccessful(userName, password)){
            val parentActivity = activity as MainActivity
            parentActivity.isUserLoggedIn = true
            userViewModel.setUserLoggedIn(parentActivity.currentUser, true)
            findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
        }
    }

    private fun checkEmailAndPassword(email : String, password : String) : Boolean{
        var toReturn = true
        var errorMessage = ""
        if (!email.matches(Regex("....*@....*\\...+"))) {
            errorMessage += "Helytelen email formátum: example@example.com"
            toReturn = false
        }
        if(!password.matches(Regex(".......*"))) {
            if(errorMessage.length > 0) errorMessage+="\n"
            errorMessage += "A jelszónak legalább 6 karakter hosszúnak kell lennie!"
            toReturn = false
        }
        return toReturn.also {
            if(!it) Toast.makeText(
                requireContext(),
                errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}