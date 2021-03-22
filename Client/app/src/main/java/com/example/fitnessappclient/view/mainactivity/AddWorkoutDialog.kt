package com.example.fitnessappclient.view.mainactivity

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.fitnessappclient.repository.entities.Workout
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import java.util.*

@Deprecated("me fos, azért")
class AddWorkoutDialog(val workoutViewModel: WorkoutViewModel) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val input = EditText(activity)
            input.hint = "Új edzés"
            input.gravity = Gravity.CENTER
            builder.setView(input)
            builder.setMessage("Létre szeretne hozni egy új edzést?")
                .setPositiveButton("igen",
                    DialogInterface.OnClickListener { dialog, id ->
                        var workoutName = if (input.length() != 0) input.text.toString() else "Új edzés"
                        workoutViewModel.addWorkout(
                            Workout(
                                0,
                                workoutViewModel.id,
                                workoutName,
                                Date(),
                                null,
                                null,
                                null
                            )
                        )
                    })
                .setNegativeButton("nem",
                    DialogInterface.OnClickListener { dialog, id -> })
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}