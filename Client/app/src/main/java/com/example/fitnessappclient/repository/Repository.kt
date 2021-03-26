package com.example.fitnessappclient.repository

import androidx.lifecycle.LiveData
import com.example.fitnessappclient.repository.daos.UserDao
import com.example.fitnessappclient.repository.entities.*
import com.example.fitnessappclient.repository.relations.UserWithWorkouts
import com.example.fitnessappclient.repository.relations.WorkoutExerciseAndExercise
import com.example.fitnessappclient.repository.relations.WorkoutPlanWithWorkoutPlanExercises
import java.util.*

class Repository(private val userDao: UserDao) {
    val allUser : LiveData<List<User>> = userDao.getUsers()
    suspend fun insertUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun insertWorkout(workout: Workout) : Long{
        return userDao.insertWorkout(workout)
    }

    suspend fun insertExercise(exercise: Exercise){
        userDao.insertExercise(exercise)
    }

    suspend fun insertWorkoutExercise(workoutExercise: WorkoutExercise) : Long{
        return userDao.insertWorkoutExercise(workoutExercise)
    }

    suspend fun insertSet(set: MySet){
        userDao.insertSet(set)
    }

    suspend fun setUserLoggedIn(userId : Long, loggedIn : Boolean){
        userDao.setUserLoggedInById(userId, loggedIn)
    }

    fun getUserLoggedInByUserId(userId : Long):LiveData<Boolean>{
        return userDao.getUserLoggedInByUserId(userId)
    }

    fun getAllSetsByWorkoutExerciseId(workoutExerciseId : Long)  : LiveData<List<MySet>>{
        return userDao.getAllSetsByWorkoutExerciseId(workoutExerciseId)
    }

    fun getAllWorkoutsByUserId(userId : Long):LiveData<List<Workout>>{
        return userDao.geWorkoutsByUserId(userId)
    }

    fun getAllExercises() : LiveData<List<Exercise>>{
        return userDao.getAllExercises()
    }

    fun getAllMeasurements() : LiveData<List<Measurement>>{
        return userDao.getAllMeasurements()
    }

    fun getAllExercisesByWorkoutId(workoutId : Long):LiveData<List<WorkoutExercise>>{
        return userDao.getAllExercisesByWorkoutId(workoutId)
    }

    fun getUserNameByUserId(userId : Long) : LiveData<String>{
        return userDao.getUserNameByUserId(userId)
    }

    suspend fun updateUserComment(workoutExerciseId: Long, clientComment: String){
        userDao.updateUserComment(workoutExerciseId,clientComment)
    }

    fun getWorkoutExerciseAndExerciseById(workoutExerciseId: Long): WorkoutExerciseAndExercise{
        return userDao.getWorkoutExerciseAndExerciseById(workoutExerciseId)
    }

    fun getAllExercisesByWorkoutType(workoutType: ExerciseType) : LiveData<List<Exercise>>{
        return userDao.getAllExercisesByWorkoutType(workoutType)
    }

    fun getAllWorkoutExercisesAndExerciseByWorkoutId(workoutId : Long) : LiveData<List<WorkoutExerciseAndExercise>>{
        return userDao.getAllWorkoutExercisesAndExerciseByWorkoutId(workoutId)
    }

    suspend fun removeWorkout(workout : Workout){
        userDao.removeWorkout(workout)
    }

    suspend fun removeSet(set : MySet){
        userDao.removeSet(set)
    }

    suspend fun removeWorkoutExercise(workoutExercise: WorkoutExercise){
        userDao.removeWorkoutExercise(workoutExercise)
    }

    fun getWorkoutsByDate(date: Date) : LiveData<List<Workout>>{
        return userDao.getWorkoutsByDate(date)
    }

    fun getAllWorkoutPlans(): LiveData<List<WorkoutPlan>>{
        return userDao.getAllWorkoutPlans()
    }

    fun getWorkoutExerciseWithWorkoutExercisesByWorkoutPlanId(workoutPlanId : Long): LiveData<WorkoutPlanWithWorkoutPlanExercises>{
        return  userDao.getWorkoutExerciseWithWorkoutExercisesByWorkoutPlanId(workoutPlanId)
    }

}