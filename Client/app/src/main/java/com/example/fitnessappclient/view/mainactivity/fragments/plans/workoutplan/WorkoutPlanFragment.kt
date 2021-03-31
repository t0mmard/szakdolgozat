package com.example.fitnessappclient.view.mainactivity.fragments.plans.workoutplan

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.WorkoutPlan
import com.example.fitnessappclient.view.mainactivity.MainActivity
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.dialog_add_workoutplan.view.*
import kotlinx.android.synthetic.main.fragment_workout_plan.view.*

class WorkoutPlanFragment : Fragment(), WorkoutPlanAdapterInterface {

    private lateinit var workoutViewModel : WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_workout_plan, container, false)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        addRecyclerView(view)

        initButtons(view)

        return view
    }

    private fun addRecyclerView(view: View){
        val adapter = WorkoutPlanAdapter(this)
        view.rv_workoutplan_list.adapter = adapter
        view.rv_workoutplan_list.layoutManager = LinearLayoutManager(requireContext())

        workoutViewModel.getAllWorkoutPlans().observe(viewLifecycleOwner, Observer { workoutPlans ->
            adapter.setData(workoutPlans)
            //if(exercises.isEmpty()) //view.tv_workoutlist_empty.visibility = View.VISIBLE
            //else                    //view.tv_workoutlist_empty.visibility = View.INVISIBLE
        })


        val itemTouchHelperCallback = getItemTouchHelper(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(view.rv_workoutplan_list)

    }

    private fun initButtons(view : View){
        view.fab_workoutplan_addworkoutplan.setOnClickListener {
            initDialog(view)
        }
    }


    private fun initDialog(view : View){

        val context = requireContext()
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_workoutplan,null)
        val parentActivity = requireActivity() as MainActivity

        dialogBuilder.setView(dialogView)

        dialogBuilder.setTitle(R.string.new_workoutplan_dialog_title)

        dialogBuilder.setPositiveButton(R.string.add,
            DialogInterface.OnClickListener { dialog, id ->

                val workoutPlanName = dialogView.et_addworkoutplan_name.text.toString()
                if (!workoutPlanName.isEmpty()) {
                    workoutViewModel.insertWorkoutPlan(
                        WorkoutPlan(
                            0,
                            workoutPlanName,
                            "",
                            true,
                            parentActivity.currentUser,
                            true
                        )
                    ).observe(viewLifecycleOwner,Observer { workoutPlanId ->
                        val action =
                            WorkoutPlanFragmentDirections.actionWorkoutPlanFragmentToAddExerciseToWorkoutplanFragment(
                                workoutPlanId
                            )
                        findNavController().navigate(action)
                    })
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
    private fun getItemTouchHelper(adapter: WorkoutPlanAdapter) : ItemTouchHelper.SimpleCallback {
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

    override fun onClick(workoutPlan: WorkoutPlan) {
        val action = WorkoutPlanFragmentDirections.actionWorkoutPlanFragmentToAddExerciseToWorkoutplanFragment(workoutPlan.workoutPlanId)
        findNavController().navigate(action)
    }
}