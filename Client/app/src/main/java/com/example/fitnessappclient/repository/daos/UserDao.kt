package com.example.fitnessappclient.repository.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fitnessappclient.repository.entities.*
import com.example.fitnessappclient.repository.relations.*
import java.util.*

@Dao
interface UserDao {

    //Adatbázis létrehozásához
    @Insert
    fun notSuspendInsertMeasurements(measurements: List<Measurement>)

    @Insert
    fun notSuspendInsertExerciseCategories(exerciseCategories: List<ExerciseCategory>)

    @Insert
    fun notSuspendInsertExercises(exercises: List<Exercise>)

    @Insert
    fun notSuspendInsertWorkoutPlans(workoutPlans: List<WorkoutPlan>)

    @Insert
    fun notSuspendInsertWorkoutPlanExercises(workoutPlanExercises: List<WorkoutPlanExercises>)


    ///////////////
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutExercise(workoutExercise: WorkoutExercise) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set : MySet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasurement(measurement: Measurement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutPlan(workoutPlan: WorkoutPlan) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutPlanExercise(workoutPlanExercises: WorkoutPlanExercises) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserMeasurement(userMeasurement: UserMeasurement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseCategory(exerciseCategory: ExerciseCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasuringSession(measuringSession: MeasuringSession) : Long

    @Delete
    suspend fun removeUserMeasurement(userMeasurement: UserMeasurement)

    @Delete
    suspend fun removeWorkoutPlanExercise(workoutPlanExercises: WorkoutPlanExercises)

    @Delete
    suspend fun removeWorkoutPlan(workoutPlan: WorkoutPlan)

    @Delete
    suspend fun removeWorkout(workout : Workout)

    @Delete
    suspend fun removeWorkoutExercise(workoutExercise : WorkoutExercise)

    @Delete
    suspend fun removeSet(set: MySet)

    @Delete
    suspend fun removeExercise(exercise: Exercise)

    @Delete
    suspend fun removeMeasurement(measurement: Measurement)

    @Delete
    suspend fun removeMeasuringSession(measuringSession: MeasuringSession)

    @Delete
    suspend fun removeExerciseCategory(exerciseCategory: ExerciseCategory)

    @Transaction
    @Query("select * from WORKOUT_CATEGORIES")
    fun getAllExerciseCategories() : LiveData<List<ExerciseCategory>>

    @Transaction
    @Query("select goalWeight from USERS where userId = :userId")
    fun getGoalWeightByUserId(userId: Long) : LiveData<Short>

    @Transaction
    @Query("update USERS set goalWeight = :goalWeight where userId = :userId")
    suspend fun setGoalWeightByUserId(userId: Long, goalWeight : Short)

    @Transaction
    @Query("select * from MEASURING_SESSION where weight != 0 order by sessionDate desc limit 1")
    fun getMostRecentMeasuringSession() : LiveData<MeasuringSession>

    @Transaction
    @Query("select * from MEASURING_SESSION order by sessionDate desc")
    fun getMeasuringSessions() : LiveData<List<MeasuringSession>>

    @Transaction
    @Query("select * from MEASURMENTS")
    fun getAllMeasurements() : LiveData<List<Measurement>>

    @Transaction
    @Query("select * from USER_MEASUREMENTS where sessionId = :sessionId")
    fun getUserMeasurementsBySessionId(sessionId : Long) : LiveData<List<UserMeasurementAndMeasurement>>

    @Transaction
    @Query("select * from EXERCISES")
    fun getAllExercises() : LiveData<List<Exercise>>

    @Transaction
    @Query("update USERS set loggedIn = :loggedIn where userId = :userId")
    suspend fun setUserLoggedInById(userId: Long, loggedIn : Boolean)

    @Transaction
    @Query("update WORKOUT_EXERCISES set clientComment = :clientComment where workoutExerciseId = :workoutExerciseId")
    suspend fun updateUserComment(workoutExerciseId: Long, clientComment: String)

    @Transaction
    @Query("update MEASURING_SESSION set weight = :weight where sessionId = :sessionId")
    suspend fun updateUserWeight(sessionId: Long, weight: Short)

    @Transaction
    @Query("select lastName from USERS where userId = :userId")
    fun getUserNameByUserId(userId : Long) : LiveData<String>

    @Transaction
    @Query("select loggedIn from USERS where userId = :userId")
    fun getUserLoggedInByUserId(userId : Long) : LiveData<Boolean>

    @Transaction
    @Query("select * from WORKOUT_EXERCISES where workoutId = :workoutId")
    fun getAllWorkoutExercisesAndExerciseByWorkoutId(workoutId: Long) : LiveData<List<WorkoutExerciseAndExercise>>

    @Transaction
    @Query("select * from WORKOUT_PLAN_EXERCISES where workoutPlanId = :workoutPlanId")
    fun getAllWorkoutPlanExercisesAndExerciseByWorkoutPlanId(workoutPlanId: Long) : LiveData<List<WorkoutPlanExerciseAndExercise>>

    @Transaction
    @Query("select * from WORKOUTS where userId = :userId")
    fun geWorkoutsByUserId(userId : Long) : LiveData<List<Workout>>

    @Transaction
    @Query("select * from SETS where workoutExerciseId = :workoutExerciseId")
    fun getAllSetsByWorkoutExerciseId(workoutExerciseId : Long) : LiveData<List<MySet>>

    @Transaction
    @Query("select * from USERS")
    fun getUsers() : LiveData<List<User>>

    @Transaction
    @Query("select * from WORKOUT_EXERCISES where workoutId = :workoutId")
    fun getAllExercisesByWorkoutId(workoutId : Long) : LiveData<List<WorkoutExercise>>

    @Transaction
    @Query("select * from WORKOUT_EXERCISES where workoutExerciseId = :workoutExerciseId")
    fun getWorkoutExerciseAndExerciseById(workoutExerciseId : Long) : WorkoutExerciseAndExercise

    @Transaction
    @Query("select * from EXERCISES where exerciseType = :workoutType")
    fun getAllExercisesByWorkoutType(workoutType: ExerciseType) : LiveData<List<Exercise>>

    @Transaction
    @Query("select * from WORKOUTS where workoutDate = :date")
    fun getWorkoutsByDate(date:Date) : LiveData<List<Workout>>

    @Transaction
    @Query("select * from WORKOUT_PLANS")
    fun getAllWorkoutPlans(): LiveData<List<WorkoutPlan>>

    @Transaction
    @Query("select * from WORKOUT_PLANS where workoutPlanId = :workoutPlanId")
    fun getWorkoutExerciseWithWorkoutExercisesByWorkoutPlanId(workoutPlanId : Long): LiveData<WorkoutPlanWithWorkoutPlanExercises>

//    @Transaction
//    @Query("SQL UTASITAS")
//    suspend fun getValamAndValamiWithValamalapjan(valamialapja): visszateres

}