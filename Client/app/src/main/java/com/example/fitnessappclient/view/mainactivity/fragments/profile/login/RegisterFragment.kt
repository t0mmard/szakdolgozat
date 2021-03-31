package com.example.fitnessappclient.view.mainactivity.fragments.profile.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fitnessappclient.R
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        initButtons(view)

        return view
    }

    private fun initButtons(view: View){
        view.btn_register_register.setOnClickListener {
            if (checkEmailAndPassword(view.et_register_username.text.toString(), view.et_register_password.text.toString() ) )
                register()
        }

        view.btn_register_tologin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun register(){
        Toast.makeText(requireContext(),"Itt lesz a regisztráció", Toast.LENGTH_SHORT).show()
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