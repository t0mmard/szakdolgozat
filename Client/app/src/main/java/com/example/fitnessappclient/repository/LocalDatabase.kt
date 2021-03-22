package com.example.fitnessappclient.repository

import android.content.Context
import androidx.room.*
import com.example.fitnessappclient.repository.daos.UserDao
import com.example.fitnessappclient.repository.entities.*

@Database(
    entities = [
        Exercise::class,
        Measurement::class,
        MeasuringSession::class,
        MySet::class,
        User::class,
        UserMeasurement::class,
        Workout::class,
        WorkoutExercise::class,
        WorkoutPlan::class,
        WorkoutPlanExercises::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "local_database"
                ).fallbackToDestructiveMigration()
                .build().also{
                    INSTANCE = it
                }
            }
        }
    }

}