package com.example.fitnessappclient.view.mainactivity

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.LocalDatabase
import com.example.fitnessappclient.repository.Repository
import com.example.fitnessappclient.repository.entities.Exercise
import com.example.fitnessappclient.repository.entities.ExerciseType
import com.example.fitnessappclient.repository.entities.User
import com.example.fitnessappclient.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {


    var selectedDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
//        val dt = Date()
//        val c = Calendar.getInstance()
//        c.time = dt
//        //c.add(Calendar.DATE, 1)
//        val formatted = SimpleDateFormat("yyyy-MM-dd").format(c.time)
        //java.sql.Date.valueOf(selectedDate.plusDays(1).toString())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Felső sávon a fragment neve jelenjen meg
        setupActionBarWithNavController(findNavController(R.id.fragment))

    //beállítjuk a fragmentnek a WorkoutListFragmentet
        /*supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, WorkoutListFragment())
            commit()
        }*/
        //val dateFormat = SimpleDateFormat("yyyy.MM.dd.", Locale("hu"))
//       val repository = Repository(LocalDatabase.getInstance(application).userDao)
//      runBlocking{
//            repository.insertExercise(
//                Exercise(
//                    0,
//                    "dummyExerciseRepetition",
//                    ExerciseType.REPETITION
//                )
//            )
//            repository.insertExercise(
//                Exercise(
//                    0,
//                    "dummyExerciseRepetitionWithWeight",
//                    ExerciseType.REPETITION_WITH_WEIGHT
//                )
//            )
//          repository.insertExercise(Exercise(0, "dummyExerciseTime", ExerciseType.TIME))
//        }
//        userViewModel.addUser(User(0, "string", "string", "string", true, false, Date()))

    }

    //mi történjen a visszagomb megnyomásánál(felső)
    override fun onSupportNavigateUp(): Boolean {
        //visszagomb imiátálsa, néhol kell a fragmentekben egyedi visszaléptetés
        dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
        dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
        return super.onSupportNavigateUp()
    }

}

