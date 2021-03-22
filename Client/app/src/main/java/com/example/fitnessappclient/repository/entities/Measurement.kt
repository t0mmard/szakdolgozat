package com.example.fitnessappclient.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "MEASURMENTS"
)
data class Measurement (
    @PrimaryKey(autoGenerate = true)
    val measurementId: Long,
    val bodyPart: String,
    val creatorId: Long
)