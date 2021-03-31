package com.example.fitnessappclient.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnessappclient.repository.LocalDatabase
import com.example.fitnessappclient.repository.Repository
import com.example.fitnessappclient.repository.entities.*
import com.example.fitnessappclient.repository.relations.UserMeasurementAndMeasurement
import com.example.fitnessappclient.repository.relations.WorkoutExerciseAndExercise
import com.example.fitnessappclient.repository.relations.WorkoutPlanExerciseAndExercise
import com.example.fitnessappclient.repository.relations.WorkoutPlanWithWorkoutPlanExercises
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun insertUserMeasurement(userMeasurement: UserMeasurement){
        viewModelScope.launch {
            repository.insertUserMeasurement(userMeasurement)
        }
    }

    fun getUserMeasurementsBySessionId(sessionId : Long) : LiveData<List<UserMeasurementAndMeasurement>>{
        return repository.getUserMeasurementsBySessionId(sessionId)
    }

    fun removeUserMeasurement(userMeasurement: UserMeasurement){
        viewModelScope.launch {
            repository.removeUserMeasurement(userMeasurement)
        }
    }

    fun insertMeasuringSession(measuringSession: MeasuringSession) : LiveData<Long>{
        val result = MutableLiveData<Long>()
        viewModelScope.launch {
            val resultLong = repository.insertMeasuringSession(measuringSession)
            result.postValue(resultLong)
        }
        return result
    }

   fun updateUserWeight(sessionId: Long, weight: Short){
       viewModelScope.launch {
           repository.updateUserWeight(sessionId, weight)
       }
    }

    fun getAllExerciseCategories() : LiveData<List<ExerciseCategory>>{
        return repository.getAllExerciseCategories()
    }

    fun removeExerciseCategory(exerciseCategory: ExerciseCategory){
        viewModelScope.launch {
            repository.removeExerciseCategory(exerciseCategory)
        }

    }

    fun insertExerciseCategory(exerciseCategory: ExerciseCategory){
        viewModelScope.launch {
            repository.insertExerciseCategory(exerciseCategory)
        }
    }

    fun getAllExercises() : LiveData<List<Exercise>>{
        return repository.getAllExercises()
    }

    fun getAllMeasurements() : LiveData<List<Measurement>>{
        return repository.getAllMeasurements()
    }

    fun getAllWorkoutPlanExercisesAndExerciseByWorkoutPlanId(workoutPlanId: Long) : LiveData<List<WorkoutPlanExerciseAndExercise>>{
        return repository.getAllWorkoutPlanExercisesAndExerciseByWorkoutPlanId(workoutPlanId)
    }

    fun addWorkout(workout: Workout): LiveData<Long>{
        var result = MutableLiveData<Long>()
        viewModelScope.launch {
            val resultLong = repository.insertWorkout(workout)
            result.postValue(resultLong)
        }
        return result
    }

    fun removeMeasuringSession(measuringSession: MeasuringSession){
        viewModelScope.launch {
            repository.removeMeasuringSession(measuringSession)
        }
    }

    fun getMostRecentMeasuringSession() : LiveData<MeasuringSession>{
        return repository.getMostRecentMeasuringSession()
    }

    fun getMeasuringSessions() : LiveData<List<MeasuringSession>>{
        return repository.getMeasuringSessions()
    }

    fun insertWorkoutPlanExercise(workoutPlanExercises: WorkoutPlanExercises) : LiveData<Long>{
        var result = MutableLiveData<Long>()
        viewModelScope.launch {
            val resultLong = repository.insertWorkoutPlanExercise(workoutPlanExercises)
            result.postValue(resultLong)
        }
        return result
    }

    fun removeWorkoutPlanExercise(workoutPlanExercises: WorkoutPlanExercises){
        viewModelScope.launch {
            repository.removeWorkoutPlanExercise(workoutPlanExercises)
        }
    }

    fun insertWorkoutPlan(workoutPlan: WorkoutPlan) : LiveData<Long>{
        var result = MutableLiveData<Long>()
        viewModelScope.launch {
            val resultLong = repository.insertWorkoutPlan(workoutPlan)
            result.postValue(resultLong)
        }
        return result
    }

    fun removeWorkoutPlan(workoutPlan: WorkoutPlan){
        viewModelScope.launch {
            repository.removeWorkoutPlan(workoutPlan)
        }
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

    fun removeExercise(exercise: Exercise){
        viewModelScope.launch {
            repository.removeExercise(exercise)
        }
    }


    fun insertExercise(exercise: Exercise){
        viewModelScope.launch {
            repository.insertExercise(exercise)
        }
    }

    fun insertMeasurement(measurement: Measurement){
        viewModelScope.launch {
            repository.insertMeasurement(measurement)
        }
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

    fun removeMeasurement(measurement: Measurement){
        viewModelScope.launch{
            repository.removeMeasurement(measurement)
        }
    }

    fun getAllWorkoutPlans(): LiveData<List<WorkoutPlan>>{
        return repository.getAllWorkoutPlans()
    }

    fun getWorkoutExerciseWithWorkoutExercisesByWorkoutPlanId(workoutPlanId : Long): LiveData<WorkoutPlanWithWorkoutPlanExercises>{
        return repository.getWorkoutExerciseWithWorkoutExercisesByWorkoutPlanId(workoutPlanId)
    }

}