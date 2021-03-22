package com.example.fitnessappclient.repository.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "MEASURING_SESSION",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("userId"),
            childColumns = arrayOf("userId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MeasuringSession (
    @PrimaryKey(autoGenerate = true)
    val sessionId: Long,
    val userId: Long,
    val weight: Short,
    val sessionDate: Date
)