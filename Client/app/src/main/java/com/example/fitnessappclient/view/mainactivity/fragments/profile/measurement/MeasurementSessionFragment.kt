package com.example.fitnessappclient.view.mainactivity.fragments.profile.measurement

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.Measurement
import com.example.fitnessappclient.repository.entities.UserMeasurement
import com.example.fitnessappclient.view.mainactivity.MainActivity
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.dialog_add_measurement_measurementsession.view.*
import kotlinx.android.synthetic.main.fragment_measurement_session.view.*

class MeasurementSessionFragment : Fragment() {

    lateinit var workoutViewModel : WorkoutViewModel
    private val args by navArgs<MeasurementSessionFragmentArgs>()
    var measurements = emptyList<Measurement>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_measurement_session, container, false)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        addRecyclerView(view)

        initButtons(view)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            workoutViewModel.updateUserWeight(args.sessionId, view.etWeight.text.toString().toShort())
            findNavController().navigate(R.id.action_measurementSessionFragment_to_profileFragment)
        }

        return view
    }



    private fun initButtons(view : View){

        view.etWeight.setText(args.weight.toString())

        view.btn_weight_minus.setOnClickListener {
            var weight = view.etWeight.text.toString().toInt()
            if(weight == 1) view.btn_weight_minus.isEnabled = false
            weight--
            view.etWeight.setText(weight.toString())
        }

        view.btn_weight_plus.setOnClickListener {
            var weight = view.etWeight.text.toString().toInt()
            if(weight == 0) view.btn_weight_minus.isEnabled = true
            weight++
            view.etWeight.setText(weight.toString())
        }

        view.fab_addmeasurement.setOnClickListener {
            initDialog(view)
        }
    }

    private fun addRecyclerView(view: View){

        val adapter = MeasurementSessionAdapter()
        view.rv_measurementsession.adapter = adapter
        view.rv_measurementsession.layoutManager = LinearLayoutManager(requireContext())

        workoutViewModel.getUserMeasurementsBySessionId(args.sessionId).observe(viewLifecycleOwner, Observer { exercises ->
            adapter.setData(exercises)
            //if(exercises.isEmpty()) //view.tv_workoutlist_empty.visibility = View.VISIBLE
            //else                    //view.tv_workoutlist_empty.visibility = View.INVISIBLE
        })

        val itemTouchHelperCallback = getItemTouchHelper(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(view.rv_measurementsession)

    }

    private fun initDialog(view : View){

        val context = requireContext()
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_measurement_measurementsession,null)
        dialogBuilder.setView(dialogView)

        dialogBuilder.setTitle(R.string.new_exercise_dialog_title)

        workoutViewModel.getAllMeasurements().observe(viewLifecycleOwner,Observer{

            measurements = it

            val measurementNames = mutableListOf<String>()

            it.forEach { measurement -> measurementNames.add(measurement.bodyPart) }

            val adapter = ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,
                measurementNames)
            dialogView.sp_measurementlist.adapter = adapter

        })



        dialogBuilder.setPositiveButton(R.string.add,
            DialogInterface.OnClickListener { dialog, id ->
                val selectedId = dialogView.sp_measurementlist.selectedItemId

                val length = dialogView.et_measurementname.text.toString()
                var lengthShort : Short = 0
                if (length.isNotEmpty()) lengthShort = length.toShort()
                val parentActivity = activity as MainActivity
                workoutViewModel.insertUserMeasurement(
                    UserMeasurement(
                        0,
                        args.sessionId,
                        measurements[selectedId.toInt()].measurementId,
                        lengthShort,
                        parentActivity.currentUser
                    )
                )
            })

        dialogBuilder.setNegativeButton(R.string.cancel,
            DialogInterface.OnClickListener { dialog, id -> })

        dialogBuilder.create().show()

    }


    //swipe to delete
    private fun getItemTouchHelper(adapter: MeasurementSessionAdapter) : ItemTouchHelper.SimpleCallback {
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