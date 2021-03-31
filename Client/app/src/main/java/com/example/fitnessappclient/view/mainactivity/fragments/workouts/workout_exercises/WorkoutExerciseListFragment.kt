package com.example.fitnessappclient.view.mainactivity.fragments.workouts.workout_exercises

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
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
import com.example.fitnessappclient.repository.entities.ExerciseType
import com.example.fitnessappclient.repository.relations.WorkoutExerciseAndExercise
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_workout_exercise_list.view.*

class WorkoutExerciseListFragment : Fragment(), WorkoutExerciseRecyclerViewListener {

    private lateinit var workoutViewModel: WorkoutViewModel
    private val args by navArgs<WorkoutExerciseListFragmentArgs>()

    //prepare view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_workout_exercise_list, container, false)

        // Visszagomb felülírása, mindig a főoldalra vigyen vissza
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_workoutExerciseListFragment_to_workoutListFragment)
        }

        //WorokutViewModel init
        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        //RecyclerViewInit
        addRecyclerViewAdapter(view)

        //Button init
        initButtons(view)

        return view
    }

    //RecyclerView
    private fun addRecyclerViewAdapter(view : View){
        val adapter = WorkoutExerciseAdapter(this)
        view.rv_workoutexercise.adapter = adapter
        view.rv_workoutexercise.layoutManager = LinearLayoutManager(requireContext())

        workoutViewModel.getAllWorkoutExercisesAndExerciseByWorkoutId(args.workoutId).observe(viewLifecycleOwner, Observer { exercises ->
            adapter.setData(exercises)
            if(exercises.isEmpty()) view.tv_exerciselist_empty.visibility = View.VISIBLE
            else view.tv_exerciselist_empty.visibility = View.INVISIBLE
        })

        val itemTouchHelperCallback = getItemTouchHelper(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(view.rv_workoutexercise)
    }

    //swipe to delete
    private fun getItemTouchHelper(adapter: WorkoutExerciseAdapter) : ItemTouchHelper.SimpleCallback {
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

    private fun initButtons(view: View){
        view.fab_workoutexercise_addworkoutexercise.setOnClickListener {
            val action =
                WorkoutExerciseListFragmentDirections.actionWorkoutExerciseListFragmentToChooseWorkoutTypeFragment(
                    args.workoutId
                )
            findNavController().navigate(action)
        }
    }



    //
    //onClick/onTouch implementáció amit a bindolt view-k használnak
    //
    override fun myOnClickListener(workoutExerciseAndExercise: WorkoutExerciseAndExercise) {
//        actionon keresztül adjuk meg a célt és az argumentumokat
        when(workoutExerciseAndExercise.exercise.exerciseType) {
            ExerciseType.REPETITION -> {
                val action =
                    WorkoutExerciseListFragmentDirections.actionWorkoutExerciseListFragmentToAddNoWeightRepsWorkoutExerciseFragment(
                        workoutExerciseAndExercise.workoutExercise.workoutId,
                        workoutExerciseAndExercise.workoutExercise.workoutExerciseId,
                        workoutExerciseAndExercise.workoutExercise.clientComment ?: ""
                    )
                findNavController().navigate(action)
            }
            ExerciseType.REPETITION_WITH_WEIGHT -> {
                val action =
                    WorkoutExerciseListFragmentDirections.actionWorkoutExerciseListFragmentToAddWeigthRepsWorkoutExerciseFragment(
                        workoutExerciseAndExercise.workoutExercise.workoutId,
                        workoutExerciseAndExercise.workoutExercise.workoutExerciseId,
                        workoutExerciseAndExercise.workoutExercise.clientComment ?: ""
                    )
                findNavController().navigate(action)
            }
            ExerciseType.TIME -> {
                val action =
                    WorkoutExerciseListFragmentDirections.actionWorkoutExerciseListFragmentToAddTimeRepsWorkoutExerciseFragment(
                        workoutExerciseAndExercise.workoutExercise.workoutId,
                        workoutExerciseAndExercise.workoutExercise.workoutExerciseId,
                        workoutExerciseAndExercise.workoutExercise.clientComment ?: ""
                    )
                findNavController().navigate(action)

            }
        }
    }

    //item háttérszín állítása érintés esetén
    override fun myOnTouchListener(view: View, motionevent: MotionEvent) : Boolean {
        //TODO: ha kéne
        return false
    }
}