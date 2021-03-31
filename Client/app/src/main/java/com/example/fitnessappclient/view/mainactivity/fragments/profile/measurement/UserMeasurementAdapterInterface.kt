package com.example.fitnessappclient.view.mainactivity.fragments.profile.measurement

import com.example.fitnessappclient.repository.entities.MeasuringSession

interface UserMeasurementAdapterInterface {
    fun onClickListener(measuringSession: MeasuringSession)
}