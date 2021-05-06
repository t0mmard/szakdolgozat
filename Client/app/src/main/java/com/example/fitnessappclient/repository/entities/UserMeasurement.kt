package com.example.fitnessappclient.repository.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "USER_MEASUREMENTS",
    foreignKeys = [
        ForeignKey(
            entity = MeasuringSession::class,
            parentColumns = arrayOf("sessionId"),
            childColumns = arrayOf("sessionId"),
            onDelete = ForeignKey.CASCADE
        )
    ,
        ForeignKey(
            entity = Measurement::class,
            parentColumns = arrayOf("measurementId"),
            childColumns = arrayOf("measurementId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserMeasurement (
    @PrimaryKey(autoGenerate = true)
    val userMeasurementId: Long,
    val sessionId: Long,
    val measurementId: Long,
    val length: Short,
    val creatorId: Long
)