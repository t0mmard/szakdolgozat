package com.example.fitnessappclient.view.mainactivity.fragments.profile.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.User
import com.example.fitnessappclient.repository.retrofit.LoginCallback
import com.example.fitnessappclient.repository.retrofit.LoginResponse
import com.example.fitnessappclient.repository.retrofit.MyRetrofit
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

        //Főoldalra küld vissza
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
        }
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
        val username = view.et_login_username.text.toString()
        val password = view.et_login_password.text.toString()
        if(checkEmailAndPassword(username, password)) {
            MyRetrofit.login(username,password, object : LoginCallback{
                override fun onSucess(loginResponse: LoginResponse) {
                    if(loginResponse.user != null){
                        warningDialog(loginResponse.user)
                    }
                    else{
                        Toast.makeText(
                            requireContext(),
                            "Nem sikerült bejelentkezni!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure() {
                    Toast.makeText(
                        requireContext(),
                        "Nem sikerült bejelentkezni!",
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

    fun warningDialog(user : User){
        val context = requireContext()
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setTitle(R.string.warning)

        dialogBuilder.setMessage(R.string.delete_warning_message)

        dialogBuilder.setPositiveButton(R.string.okay,
        DialogInterface.OnClickListener{dialog,id ->
            val parentActivity = activity as MainActivity
            if(user.userId != -1L)parentActivity.isUserLoggedIn = true
            parentActivity.currentUser = user.userId
            userViewModel.addUser(user)
            userViewModel.setUserLoggedIn(user.userId, true)
            userViewModel.deleteAllTables()

            //TODO adatbázis törlése és szinkronizáció
            findNavController().navigate(R.id.action_loginFragment_to_profileFragment)

        })

        dialogBuilder.setNegativeButton(R.string.cancel,
        DialogInterface.OnClickListener{dialog,id ->})

        dialogBuilder.create().show()

    }

}