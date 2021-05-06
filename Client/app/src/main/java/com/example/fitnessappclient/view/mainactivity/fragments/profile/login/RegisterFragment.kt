package com.example.fitnessappclient.view.mainactivity.fragments.profile.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.retrofit.MyRetrofit
import com.example.fitnessappclient.repository.retrofit.RegisterCallback
import com.example.fitnessappclient.view.mainactivity.MainActivity
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        //Főoldalra küld vissza
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_registerFragment_to_profileFragment)
        }

        initButtons(view)

        return view
    }

    private fun initButtons(view: View){
        view.btn_register_register.setOnClickListener {
            if (checkEmailAndPassword(view.et_register_username.text.toString(), view.et_register_password.text.toString() ) )
                register(view)
        }

        view.btn_register_tologin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun register(view:View){
        val username = view.et_register_username.text.toString()
        val password = view.et_register_password.text.toString()

        if (checkEmailAndPassword(username, password)){
            val parentActivity = activity as MainActivity
            MyRetrofit.register(username,password, object : RegisterCallback{
                override fun onSucess() {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }

                override fun onFailure() {
                    Toast.makeText(
                        requireContext(),
                        "Nem sikerült regisztrálni!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
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