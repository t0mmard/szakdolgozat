package com.example.fitnessappclient.repository.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fitnessappclient.repository.entities.Measurement
import com.example.fitnessappclient.repository.entities.User
import com.example.fitnessappclient.repository.entities.UserMeasurement
import com.example.fitnessappclient.repository.entities.Workout

data class UserMeasurementAndMeasurement (
    @Embedded val userMeasurement: UserMeasurement,
    @Relation(
        parentColumn = "measurementId",
        entityColumn = "measurementId"
    )
    val measurement: Measurement
)