package com.example.fitnessappclient.view.mainactivity.fragments.workout_exercises

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
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
import com.example.fitnessappclient.repository.entities.WorkoutExercise
import com.example.fitnessappclient.repository.relations.WorkoutExerciseAndExercise
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_add_weight_reps_workout_exercise.view.*
import kotlinx.android.synthetic.main.fragment_workout_exercise_list.*
import kotlinx.android.synthetic.main.fragment_workout_exercise_list.view.*
import kotlinx.android.synthetic.main.fragment_workout_list.view.*

class WorkoutExerciseListFragment : Fragment(), WorkoutExerciseRecyclerViewListener {

    private lateinit var workoutViewModel: WorkoutViewModel
    private val args by navArgs<WorkoutExerciseListFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Visszagomb felülírása, mindig a főoldalra vigyen vissza
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_workoutExerciseListFragment_to_workoutListFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_workout_exercise_list, container, false)

        //RecyclerView init
        val adapter = WorkoutExerciseAdapter(this)
        view.rvWorkoutExercises.adapter = adapter
        view.rvWorkoutExercises.layoutManager = LinearLayoutManager(requireContext())

        //WorokutViewModel init
        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        workoutViewModel.getAllWorkoutExercisesAndExerciseByWorkoutId(args.workoutId).observe(viewLifecycleOwner, Observer { exercises ->
            adapter.setData(exercises)
            if(exercises.isEmpty()) view.tv_exerciselist_empty.visibility = View.VISIBLE
            else view.tv_exerciselist_empty.visibility = View.INVISIBLE
        })

        view.fabAddExercise.setOnClickListener {
            val action = WorkoutExerciseListFragmentDirections.actionWorkoutExerciseListFragmentToChooseWorkoutTypeFragment(args.workoutId)
            findNavController().navigate(action)
        }


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeAt(viewHolder.adapterPosition, workoutViewModel)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(view.rvWorkoutExercises)


        return view
    }

    //onClick/onTouch implementáció amit a bindolt view-k használnak
    override fun myOnClickListener(workoutExerciseAndExercise: WorkoutExerciseAndExercise) {
//        actionon keresztül adjuk meg a célt és az argumentumokat
        when(workoutExerciseAndExercise.exercise.exerciseType) {
            ExerciseType.REPETITION -> {
                val action = WorkoutExerciseListFragmentDirections
                    .actionWorkoutExerciseListFragmentToAddNoWeightRepsWorkoutExerciseFragment(
                        workoutExerciseAndExercise.workoutExercise.workoutId,
                        workoutExerciseAndExercise.workoutExercise.workoutExerciseId,
                        workoutExerciseAndExercise.workoutExercise.clientComment?:""
                    )
                findNavController().navigate(action)
            }
            ExerciseType.REPETITION_WITH_WEIGHT -> {
                val action = WorkoutExerciseListFragmentDirections
                    .actionWorkoutExerciseListFragmentToAddWeigthRepsWorkoutExerciseFragment(
                        workoutExerciseAndExercise.workoutExercise.workoutId,
                        workoutExerciseAndExercise.workoutExercise.workoutExerciseId,
                        workoutExerciseAndExercise.workoutExercise.clientComment?:""
                    )
                findNavController().navigate(action)
            }
            ExerciseType.TIME -> {
                val action = WorkoutExerciseListFragmentDirections
                .actionWorkoutExerciseListFragmentToAddTimeRepsWorkoutExerciseFragment(
                    workoutExerciseAndExercise.workoutExercise.workoutId,
                    workoutExerciseAndExercise.workoutExercise.workoutExerciseId,
                    workoutExerciseAndExercise.workoutExercise.clientComment?:""
                )
                findNavController().navigate(action)

            }
        }
    }

    //item háttérszín állítása érintés esetén
    override fun myOnTouchListener(view: View, motionevent: MotionEvent) : Boolean {

        val color1 = ContextCompat.getColor(activity as Context, R.color.periwinkle_trans)
        val color2 = ContextCompat.getColor(activity as Context, R.color.periwinkle)

        when(motionevent.action){
            MotionEvent.ACTION_DOWN -> {
                animateBackground(
                    color1,
                    color2,
                    view,
                    400L)
            }
            MotionEvent.ACTION_UP -> {
                animateBackground(
                    color2,
                    color1,
                    view,
                    400L)
                view.performClick()
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                animateBackground(
                    color2,
                    color1,
                    view,
                    400L)
            }
            else ->{
                animateBackground(
                    color2,
                    color1,
                    view,
                    200L)
            }
        }
        return false
    }

    private fun animateBackground( startColor : Int, endColor : Int, view : View, duration : Long){
        val backgroundColorAnimator = ObjectAnimator.ofObject(
            view,
            "backgroundColor",
            ArgbEvaluator(),
            startColor,
            endColor
        )
        backgroundColorAnimator.duration = duration
        backgroundColorAnimator.start()
    }

}