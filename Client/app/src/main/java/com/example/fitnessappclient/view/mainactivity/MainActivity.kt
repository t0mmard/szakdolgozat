package com.example.fitnessappclient.view.mainactivity

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.fitnessappclient.R
import com.example.fitnessappclient.viewmodel.UserViewModel
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    var selectedDate = LocalDate.now()
    var isUserLoggedIn = false
    var currentUser = -2L //-2L default user
    lateinit var userViewModel : UserViewModel
    lateinit var workoutViewModel : WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Felső sávon a fragment neve jelenjen meg
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.profileFragment,
            R.id.planFragment,
            R.id.workoutListFragment
        ).build()

        //deleteDatabase("local_database")

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.startup()
        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        //user betöltése
        userViewModel.getAllUsersFromDB().observe(this, Observer { users ->
            for(user in users){
                if(user.loggedIn){
                    isUserLoggedIn = true
                    currentUser = user.userId
                }
            }
        })

        setupActionBarWithNavController(findNavController(R.id.fragment_mainactivity),appBarConfiguration)

        //TODO teszt törölni kell
        workoutViewModel.fullSynchronization()

    }

    //mi történjen a visszagomb megnyomásánál(felső)
    override fun onSupportNavigateUp(): Boolean {
        //visszagomb imiátálsa, néhol kell a fragmentekben egyedi visszaléptetés
        dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
        dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
        return super.onSupportNavigateUp()
    }

}

