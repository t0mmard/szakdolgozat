package com.example.fitnessappclient.view.mainactivity.fragments.plans.workoutplan

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_add_exercise_to_workoutplan.view.*
import kotlinx.android.synthetic.main.fragment_measurement_list.view.*

class AddExerciseToWorkoutplanFragment : Fragment() {

    private lateinit var workoutViewModel: WorkoutViewModel

    private val args by navArgs<AddExerciseToWorkoutplanFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_exercise_to_workoutplan, container, false)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        addRecyclerView(view)

        initButtons(view)

        return view

    }

    private fun initButtons(view: View){

        view.fab_addexercisetoworkoutplan_addmeasurement.setOnClickListener {
            val action =
                AddExerciseToWorkoutplanFragmentDirections.actionAddExerciseToWorkoutplanFragmentToChooseWorkoutPlanExerciseTypeFragment(
                    args.workoutPlanId
                )
            findNavController().navigate(action)
        }
    }

    private fun addRecyclerView(view : View){
        val adapter = WorkoutPlanExerciseAdapter()
        view.rv_addexercisetoworkoutplan.adapter = adapter
        view.rv_addexercisetoworkoutplan.layoutManager = LinearLayoutManager(requireContext())

        workoutViewModel.getAllWorkoutPlanExercisesAndExerciseByWorkoutPlanId(args.workoutPlanId).observe(viewLifecycleOwner, Observer { exercises ->
            adapter.setData(exercises)
            //if(exercises.isEmpty()) //view.tv_workoutlist_empty.visibility = View.VISIBLE
            //else                    //view.tv_workoutlist_empty.visibility = View.INVISIBLE
        })

        val itemTouchHelperCallback = getItemTouchHelper(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(view.rv_measurements_list)

    }

    //swipe to delete
    private fun getItemTouchHelper(adapter: WorkoutPlanExerciseAdapter) : ItemTouchHelper.SimpleCallback {
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