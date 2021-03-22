package com.example.fitnessappclient.repository.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fitnessappclient.repository.entities.*
import com.example.fitnessappclient.repository.relations.UserWithWorkouts
import com.example.fitnessappclient.repository.relations.WorkoutExerciseAndExercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

@Dao
interface UserDao {

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

    @Delete
    suspend fun removeWorkout(workout : Workout)

    @Delete
    suspend fun removeWorkoutExercise(workoutExercise : WorkoutExercise)

    @Delete
    suspend fun removeSet(set: MySet)

    @Query("update WORKOUT_EXERCISES set clientComment = :clientComment where workoutExerciseId = :workoutExerciseId")
    suspend fun updateUserComment(workoutExerciseId: Long, clientComment: String)

    @Transaction
    @Query("select * from WORKOUT_EXERCISES where workoutId = :workoutId")
    fun getAllWorkoutExercisesAndExerciseByWorkoutId(workoutId: Long) : LiveData<List<WorkoutExerciseAndExercise>>

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

//    @Transaction
//    @Query("SQL UTASITAS")
//    suspend fun getValamAndValamiWithValamalapjan(valamialapja): visszateres

}