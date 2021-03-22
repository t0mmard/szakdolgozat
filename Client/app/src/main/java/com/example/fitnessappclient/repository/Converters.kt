package com.example.fitnessappclient.repository

import androidx.room.TypeConverter
import com.example.fitnessappclient.repository.entities.ExerciseType
import java.util.*

class Converters {

    //Date to long
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    //Enum to String
    @TypeConverter
    fun toType(value: String) = enumValueOf<ExerciseType>(value)

    @TypeConverter
    fun fromHealth(value: ExerciseType) = value.name
}