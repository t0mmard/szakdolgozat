package com.example.fitnessappclient.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessappclient.repository.daos.UserDao
import com.example.fitnessappclient.repository.entities.*
import com.example.fitnessappclient.repository.relations.*
import java.util.*

class Repository(private val userDao: UserDao) {
    val allUser : LiveData<List<User>> = userDao.getUsers()

   suspend  fun startup(){
        userDao.startup()
    }

    suspend fun insertUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun insertWorkout(workout: Workout) : Long{
        return userDao.insertWorkout(workout)
    }

    suspend fun insertUserMeasurement(userMeasurement: UserMeasurement){
        userDao.insertUserMeasurement(userMeasurement)
    }

    suspend fun insertExercise(exercise: Exercise){
        userDao.insertExercise(exercise)
    }

    suspend fun insertWorkoutExercise(workoutExercise: WorkoutExercise) : Long{
        return userDao.insertWorkoutExercise(workoutExercise)
    }

    suspend fun insertMeasurement(measurement: Measurement){
        userDao.insertMeasurement(measurement)
    }

    suspend fun insertSet(set: MySet){
        userDao.insertSet(set)
    }

    suspend fun insertMeasuringSession(measuringSession: MeasuringSession) : Long{
        return userDao.insertMeasuringSession(measuringSession)
    }

    fun getAllWorkoutPlanExercisesAndExerciseByWorkoutPlanId(workoutPlanId: Long) : LiveData<List<WorkoutPlanExerciseAndExercise>>{
        return userDao.getAllWorkoutPlanExercisesAndExerciseByWorkoutPlanId(workoutPlanId)
    }

    suspend fun setUserLoggedIn(userId : Long, loggedIn : Boolean){
        userDao.setUserLoggedInById(userId, loggedIn)
    }

    suspend fun insertWorkoutPlan(workoutPlan: WorkoutPlan) : Long{
       return userDao.insertWorkoutPlan(workoutPlan)
    }

    suspend fun insertWorkoutPlanExercise(workoutPlanExercises: WorkoutPlanExercises) : Long{
        return userDao.insertWorkoutPlanExercise(workoutPlanExercises)
    }

    suspend fun removeWorkoutPlanExercise(workoutPlanExercises: WorkoutPlanExercises){
        return userDao.removeWorkoutPlanExercise(workoutPlanExercises)
    }

    suspend fun removeWorkoutPlan(workoutPlan: WorkoutPlan){
        userDao.removeWorkoutPlan(workoutPlan)
    }

    fun getUserLoggedInByUserId(userId : Long):LiveData<Boolean>{
        return userDao.getUserLoggedInByUserId(userId)
    }

    fun getUserMeasurementsBySessionId(sessionId : Long) : LiveData<List<UserMeasurementAndMeasurement>>{
        return userDao.getUserMeasurementsBySessionId(sessionId)
    }

    suspend fun removeUserMeasurement(userMeasurement: UserMeasurement){
        userDao.removeUserMeasurement(userMeasurement)
    }

    fun getAllSetsByWorkoutExerciseId(workoutExerciseId : Long)  : LiveData<List<MySet>>{
        return userDao.getAllSetsByWorkoutExerciseId(workoutExerciseId)
    }

    fun getAllWorkoutsByUserId(userId : Long):LiveData<List<Workout>>{
        return userDao.geWorkoutsByUserId(userId)
    }

    suspend fun updateUserWeight(sessionId: Long, weight: Short){
        userDao.updateUserWeight(sessionId, weight)
    }

    suspend fun removeMeasuringSession(measuringSession: MeasuringSession){
        userDao.removeMeasuringSession(measuringSession)
    }

    suspend fun setGoalWeightByUserId(userId: Long, goalWeight : Short){
        userDao.setGoalWeightByUserId(userId, goalWeight)
    }

    fun getGoalWeightByUserId(userId: Long) : LiveData<Short>{
        return userDao.getGoalWeightByUserId(userId)
    }

    fun getAllExerciseCategories() : LiveData<List<ExerciseCategory>>{
        return userDao.getAllExerciseCategories()
    }

    suspend fun removeExerciseCategory(exerciseCategory: ExerciseCategory){
        userDao.removeExerciseCategory(exerciseCategory)
    }

    suspend fun insertExerciseCategory(exerciseCategory: ExerciseCategory){
        userDao.insertExerciseCategory(exerciseCategory)
    }

    fun getMostRecentMeasuringSession() : LiveData<MeasuringSession>{
        return userDao.getMostRecentMeasuringSession()
    }

    fun getMeasuringSessions() : LiveData<List<MeasuringSession>>{
        return userDao.getMeasuringSessions()
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

    suspend fun removeExercise(exercise: Exercise){
        userDao.removeExercise(exercise)
    }

    suspend fun removeMeasurement(measurement: Measurement){
        userDao.removeMeasurement(measurement)
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

    suspend fun getAllSets() : List<MySet>{
        return userDao.getAllSets()
    }

    suspend fun getAllUserMeasurements() : List<UserMeasurement>{
        return userDao.getAllUserMeasurements()
    }

    suspend fun getAllWorkouts() : List<Workout>{
        return userDao.getAllWorkouts()
    }

    suspend fun getAllWorkoutExercises() : List<WorkoutExercise>{
        return userDao.getAllWorkoutExercises()
    }

    fun getUserByUserId(id : Long) : LiveData<User>{
        return userDao.getUserByUserId(id)
    }

    suspend fun getAllWorkoutPlanExercises() : List<WorkoutPlanExercises>{
        return userDao.getAllWorkoutPlanExercises()
    }

    fun getAllUsers() : LiveData<List<User>>{
        return userDao.getAllUsers()
    }

    suspend fun getSuspendUserByUserId(userId: Long): User {
        return userDao.getSuspendUserbyUserId(userId)
    }

    suspend fun deleteAllTables() : Boolean{
        userDao.deleteAllExercises()
        userDao.deleteAllExerciseCategories()
        userDao.deleteAllMeasurements()
        userDao.deleteAllMeasuringSessions()
        userDao.deleteAllSets()
        userDao.deleteAllUserMeasurements()
        userDao.deleteAllWorkouts()
        userDao.deleteAllWorkoutExercises()
        userDao.deleteAllWorkoutPlans()
        userDao.deleteAllWorkoutPlanExercises()
        return true
    }

}