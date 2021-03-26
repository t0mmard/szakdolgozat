package com.example.fitnessappclient.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnessappclient.repository.LocalDatabase
import com.example.fitnessappclient.repository.Repository
import com.example.fitnessappclient.repository.entities.*
import com.example.fitnessappclient.repository.relations.WorkoutExerciseAndExercise
import com.example.fitnessappclient.repository.relations.WorkoutPlanWithWorkoutPlanExercises
import kotlinx.coroutines.launch
import java.util.*

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    //TODO beszerezni dinamikusan bár lehet felesleges... (nem)
    val id = 1L

    init{
        val userDao = LocalDatabase.getInstance(application).userDao
        repository = Repository(userDao)
    }

    fun getWorkoutsByDate(date:Date) : LiveData<List<Workout>>{
        return repository.getWorkoutsByDate(date)
    }

    fun getAllExercises() : LiveData<List<Exercise>>{
        return repository.getAllExercises()
    }

    fun getAllMeasurements() : LiveData<List<Measurement>>{
        return repository.getAllMeasurements()
    }

    fun addWorkout(workout: Workout): LiveData<Long>{
        var result = MutableLiveData<Long>()
        viewModelScope.launch {
            val resultLong = repository.insertWorkout(workout)
            result.postValue(resultLong)
        }
        return result
    }

    fun getAllWorkoutExercisesByWorkoutId(workoutId : Long) : LiveData<List<WorkoutExercise>>{
        return repository.getAllExercisesByWorkoutId(workoutId)
    }

    fun updateUserComment(workoutExerciseId: Long, clientComment: String){
        viewModelScope.launch {
            repository.updateUserComment(workoutExerciseId,clientComment)
        }
    }

    fun getAllWorkoutExercisesAndExerciseByWorkoutId(workoutId : Long) : LiveData<List<WorkoutExerciseAndExercise>>{
        return repository.getAllWorkoutExercisesAndExerciseByWorkoutId(workoutId)
    }

    fun getAllExercisesByWorkoutType(workoutType: ExerciseType) : LiveData<List<Exercise>>{
        return repository.getAllExercisesByWorkoutType(workoutType)
    }

    fun getWorkoutExerciseAndExerciseById(workoutExerciseId : Long): WorkoutExerciseAndExercise {
        return repository.getWorkoutExerciseAndExerciseById(workoutExerciseId)
    }

    fun insertSet(set: MySet){
        viewModelScope.launch {
            repository.insertSet(set)
        }
    }

    fun getAllSetsByWorkoutExerciseId(workoutExerciseId : Long) : LiveData<List<MySet>>{
        return repository.getAllSetsByWorkoutExerciseId(workoutExerciseId)
    }

    fun insertWorkoutExercise(workoutExercise: WorkoutExercise) : LiveData<Long>{
        var result = MutableLiveData<Long>()
        viewModelScope.launch {
            val resultLong = repository.insertWorkoutExercise(workoutExercise)
            result.postValue(resultLong)
        }
        return result
    }

    fun removeWorkout(workout : Workout){
        viewModelScope.launch {
            repository.removeWorkout(workout)
        }
    }

    fun removeSet(set : MySet){
        viewModelScope.launch {
            repository.removeSet(set)
        }
    }

    fun removeWorkoutExercise(workoutExercise: WorkoutExercise){
        viewModelScope.launch {
            repository.removeWorkoutExercise(workoutExercise)
        }
    }

    fun getAllWorkoutPlans(): LiveData<List<WorkoutPlan>>{
        return repository.getAllWorkoutPlans()
    }

    fun getWorkoutExerciseWithWorkoutExercisesByWorkoutPlanId(workoutPlanId : Long): LiveData<WorkoutPlanWithWorkoutPlanExercises>{
        return repository.getWorkoutExerciseWithWorkoutExercisesByWorkoutPlanId(workoutPlanId)
    }

}