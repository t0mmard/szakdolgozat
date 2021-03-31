package com.example.fitnessappclient.view.mainactivity.fragments.plans.measurement

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.Measurement
import com.example.fitnessappclient.view.mainactivity.MainActivity
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.dialog_add_measurement.view.*
import kotlinx.android.synthetic.main.fragment_measurement_list.view.*

class MeasurementListFragment : Fragment() {

    private lateinit var workoutViewModel : WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_measurement_list, container, false)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        addRecyclerView(view)

        initButtons(view)


        return view
    }

    private fun initButtons(view : View){


        view.fab_measurements_addmeasurement.setOnClickListener {
            initDialog(view)
        }

    }

    private fun addRecyclerView(view: View){

        val adapter = MeasurementAdapter()
        view.rv_measurements_list.adapter = adapter
        view.rv_measurements_list.layoutManager = LinearLayoutManager(requireContext())

        workoutViewModel.getAllMeasurements().observe(viewLifecycleOwner, Observer { measurements ->
            adapter.setData(measurements)
            //if(exercises.isEmpty()) //view.tv_workoutlist_empty.visibility = View.VISIBLE
            //else                    //view.tv_workoutlist_empty.visibility = View.INVISIBLE
        })

        val itemTouchHelperCallback = getItemTouchHelper(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(view.rv_measurements_list)

    }

    private fun initDialog(view : View){

        val context = requireContext()
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_measurement,null)
        dialogBuilder.setView(dialogView)

        dialogBuilder.setTitle(R.string.new_measurement_dialog_title)

        dialogBuilder.setPositiveButton(R.string.add,
            DialogInterface.OnClickListener { dialog, id ->

                val exerciseName = dialogView.et_addmeasurement_name.text.toString()
                if (!exerciseName.isEmpty()) {
                    workoutViewModel.insertMeasurement(
                        Measurement(
                            0,
                            exerciseName,
                            (activity as MainActivity).currentUser,
                            true
                        )
                    )
                }
                else{
                    Toast.makeText(context, "Név megadása kötlező", Toast.LENGTH_SHORT).show()
                }
            })

        dialogBuilder.setNegativeButton(R.string.cancel,
            DialogInterface.OnClickListener { dialog, id -> })

        dialogBuilder.create().show()

    }


    //swipe to delete
    private fun getItemTouchHelper(adapter: MeasurementAdapter) : ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                val itemView = viewHolder.itemView
                val trashIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_delete_24 ) as Drawable
                trashIcon.setBounds(itemView.left + itemView.paddingLeft, itemView.top + itemView.paddingTop,
                    itemView.left + itemView.paddingLeft + ((itemView.bottom - itemView.paddingBottom) - (itemView.top + itemView.paddingTop)), //jobb oldal a baltól olyan távol mint a teteje az aljától
                    itemView.bottom - itemView.paddingBottom)
                trashIcon.draw(c)

                super.onChildDraw( c,recyclerView,viewHolder, dX, dY, actionState, isCurrentlyActive)

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeAt(viewHolder.adapterPosition, workoutViewModel)
            }
        }
    }

}