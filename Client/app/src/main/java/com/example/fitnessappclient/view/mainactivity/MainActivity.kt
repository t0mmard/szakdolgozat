package com.example.fitnessappclient.view.mainactivity

import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.User
import com.example.fitnessappclient.repository.retrofit.MyRetrofit
import com.example.fitnessappclient.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    var selectedDate = LocalDate.now()
    var  isUserLoggedIn = false
    var currentUser = 1L
    lateinit var userViewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Felső sávon a fragment neve jelenjen meg
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.profileFragment,
            R.id.planFragment,
            R.id.workoutListFragment
        ).build()

        //kapcsolat teszt
        val rf = MyRetrofit("admin","admin")
        rf.getAnswer().observe(this, androidx.lifecycle.Observer {
            println(it.string)
            println(it.stringArray)
        })

        deleteDatabase("local_database")

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val loggedInLiveData = userViewModel.getUserLoggedInByUserId(currentUser)
        loggedInLiveData.observe(this, androidx.lifecycle.Observer { loggedIn ->
            loggedInLiveData.removeObservers(this)
            if(loggedIn == null){
                userViewModel.addUser(
                    User(
                        0,
                        "none",
                        "defaultUser",
                        "none",
                        true,
                        0,
                        false,
                        Date()
                    )
                )
            }
            else{
                isUserLoggedIn = loggedIn
            }
        })

        setupActionBarWithNavController(findNavController(R.id.fragment_mainactivity),appBarConfiguration)

    }

    //mi történjen a visszagomb megnyomásánál(felső)
    override fun onSupportNavigateUp(): Boolean {
        //visszagomb imiátálsa, néhol kell a fragmentekben egyedi visszaléptetés
        dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
        dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
        return super.onSupportNavigateUp()
    }

}

