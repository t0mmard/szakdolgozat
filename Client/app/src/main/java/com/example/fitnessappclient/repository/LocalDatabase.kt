package com.example.fitnessappclient.repository

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fitnessappclient.repository.daos.UserDao
import com.example.fitnessappclient.repository.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.invoke
import kotlinx.coroutines.supervisorScope
import java.util.concurrent.Executors

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
        WorkoutPlanExercises::class,
        ExerciseCategory::class
    ],
    version = 10,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract val userDao : UserDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

        fun getInstance(context: Context): LocalDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "local_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(object : Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            ioThread {
                                prePopulate(context)
                            }
                        }
                    })
                .build().also{
                    INSTANCE = it
                }
            }
        }

        private fun ioThread(f : () -> Unit ){
            IO_EXECUTOR.execute(f)
        }

        private fun prePopulate(context : Context){
            val userDao = getInstance(context).userDao

            //Testméretek
            val measurementsList = mutableListOf<Measurement>()
            measurementsList.add( Measurement(0, "Bicepsz", -1,false))
            measurementsList.add( Measurement(0, "Vádli", -1,false))
            measurementsList.add( Measurement(0, "Alkar", -1,false))
            measurementsList.add( Measurement(0, "Mell", -1,false))
            measurementsList.add( Measurement(0, "Derék", -1,false))
            measurementsList.add( Measurement(0, "Csípő", -1,false))
            measurementsList.add( Measurement(0, "Comb", -1,false))
            measurementsList.add( Measurement(0, "Nyak", -1,false))
            measurementsList.add( Measurement(0, "Csukló", -1,false))
            measurementsList.add( Measurement(0, "Boka", -1,false))

            userDao.notSuspendInsertMeasurements(measurementsList)

            //Kategóriák
            val exerciseCategoryList = mutableListOf<ExerciseCategory>()
            exerciseCategoryList.add(ExerciseCategory(0,"Váll", -1, false))     //1
            exerciseCategoryList.add(ExerciseCategory(0,"Tricepsz", -1, false)) //2
            exerciseCategoryList.add(ExerciseCategory(0,"Bicepsz", -1, false))  //3
            exerciseCategoryList.add(ExerciseCategory(0,"Mell", -1, false))     //4
            exerciseCategoryList.add(ExerciseCategory(0,"Hát", -1, false))      //5
            exerciseCategoryList.add(ExerciseCategory(0,"Láb", -1, false))      //6
            exerciseCategoryList.add(ExerciseCategory(0,"Has", -1, false))      //7
            exerciseCategoryList.add(ExerciseCategory(0,"Kardió", -1, false))   //8

            userDao.notSuspendInsertExerciseCategories(exerciseCategoryList)

            //Gyakorlatok
            val exercisesList = mutableListOf<Exercise>()
            exercisesList.add(Exercise(0,"Fekvenyomás",4,ExerciseType.REPETITION_WITH_WEIGHT, false))                   //1
            exercisesList.add(Exercise(0,"Fekvőtámasz",4,ExerciseType.REPETITION, false))                               //2
            exercisesList.add(Exercise(0,"Fekvőtámasz (szűk)",4,ExerciseType.REPETITION, false))                        //3
            exercisesList.add(Exercise(0,"Fekvőtámasz (széles)",4,ExerciseType.REPETITION, false))                      //4
            exercisesList.add(Exercise(0,"Fekvőtámasz kézállásban",1,ExerciseType.REPETITION, false))                   //5
            exercisesList.add(Exercise(0,"\"Pike\" fekvőtámasz",1,ExerciseType.REPETITION, false))                      //6
            exercisesList.add(Exercise(0,"Vállból nyomás",1,ExerciseType.REPETITION_WITH_WEIGHT, false))                //7
            exercisesList.add(Exercise(0,"Tolódzkodás",2,ExerciseType.REPETITION, false))                               //8
            exercisesList.add(Exercise(0,"Csigás letolás",2,ExerciseType.REPETITION_WITH_WEIGHT, false))                //9
            exercisesList.add(Exercise(0,"Szűknyomás",2,ExerciseType.REPETITION_WITH_WEIGHT, false))                    //10
            exercisesList.add(Exercise(0,"Bicepsz egykezes súlyzóval",3,ExerciseType.REPETITION_WITH_WEIGHT, false))    //11
            exercisesList.add(Exercise(0,"Bicepsz franciarúddal",3,ExerciseType.REPETITION_WITH_WEIGHT, false))         //12
            exercisesList.add(Exercise(0,"Evezés",3,ExerciseType.REPETITION_WITH_WEIGHT, false))                        //13
            exercisesList.add(Exercise(0,"Húzódzkodás",5,ExerciseType.REPETITION, false))                               //14
            exercisesList.add(Exercise(0,"Lehúzás mellhez szélesen",5,ExerciseType.REPETITION_WITH_WEIGHT, false))      //15
            exercisesList.add(Exercise(0,"Döntött törzsű evezés",5,ExerciseType.REPETITION_WITH_WEIGHT, false))         //16
            exercisesList.add(Exercise(0,"Evezés ülve",5,ExerciseType.REPETITION_WITH_WEIGHT, false))                   //17
            exercisesList.add(Exercise(0,"Guggolás",6,ExerciseType.REPETITION, false))                                  //18
            exercisesList.add(Exercise(0,"Guggolás súllyal",6,ExerciseType.REPETITION_WITH_WEIGHT, false))              //19
            exercisesList.add(Exercise(0,"Felülés",7,ExerciseType.REPETITION, false))                                   //20
            exercisesList.add(Exercise(0,"Hasprés",7,ExerciseType.REPETITION, false))                                   //21
            exercisesList.add(Exercise(0,"Lábemelés",7,ExerciseType.REPETITION, false))                                 //22
            exercisesList.add(Exercise(0,"Plank",7,ExerciseType.REPETITION, false))                                     //23
            exercisesList.add(Exercise(0,"Futás",8,ExerciseType.TIME, false))                                           //24
             exercisesList.add(Exercise(0,"Bicikli",8,ExerciseType.TIME, false))                                        //25
            exercisesList.add(Exercise(0,"Túra",8,ExerciseType.TIME, false))                                            //26
            exercisesList.add(Exercise(0,"Úszás",8,ExerciseType.TIME, false))                                           //27

            userDao.notSuspendInsertExercises(exercisesList)

            //Edzéstervek
            val workoutPlanList = mutableListOf<WorkoutPlan>()
            workoutPlanList.add(WorkoutPlan(0,"Saját testsúlyos edzés","",false,-1,false))  //1
            workoutPlanList.add(WorkoutPlan(0,"Súlyzós edzés","",false,-1,false))           //2

            userDao.notSuspendInsertWorkoutPlans(workoutPlanList)

            //Edzésterv gaykorlatok
            val workoutPlanExercisesList = mutableListOf<WorkoutPlanExercises>()
            //saját testsúlyos
            workoutPlanExercisesList.add(WorkoutPlanExercises(0,1, 18))
            workoutPlanExercisesList.add(WorkoutPlanExercises(0, 1, 14))
            workoutPlanExercisesList.add(WorkoutPlanExercises(0, 1, 6))
            workoutPlanExercisesList.add(WorkoutPlanExercises(0, 1, 22))
            workoutPlanExercisesList.add(WorkoutPlanExercises(0, 1, 8))
            workoutPlanExercisesList.add(WorkoutPlanExercises(0, 1, 2))
            workoutPlanExercisesList.add(WorkoutPlanExercises(0, 1, 23))

            //súlyzós
            workoutPlanExercisesList.add(WorkoutPlanExercises(0, 2, 1))
            workoutPlanExercisesList.add(WorkoutPlanExercises(0, 2, 7))
            workoutPlanExercisesList.add(WorkoutPlanExercises(0, 2, 9))
            workoutPlanExercisesList.add(WorkoutPlanExercises(0, 2, 12))
            workoutPlanExercisesList.add(WorkoutPlanExercises(0, 2, 16))
            workoutPlanExercisesList.add(WorkoutPlanExercises(0, 2, 19))

            userDao.notSuspendInsertWorkoutPlanExercises(workoutPlanExercisesList)
        }
    }

}