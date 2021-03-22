package com.example.fitnessappclient.view.mainactivity.fragments.workouts

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.Workout
import com.example.fitnessappclient.view.mainactivity.MainActivity
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_workout_list.*
import kotlinx.android.synthetic.main.fragment_workout_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class WorkoutListFragment : Fragment(), WorkoutRecyclerViewListener {

    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workout_list, container, false)

        //RecyclerView init
        val adapter = WorkoutAdapter(this)
        view.rvWorkouts.layoutManager = LinearLayoutManager(requireContext())

        val main = activity as MainActivity

        //WorokutViewModel init
        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        workoutViewModel.getWorkoutsByDate(java.sql.Date.valueOf(main.selectedDate.toString())).observe(viewLifecycleOwner, Observer { workouts ->
            adapter.setData(workouts)
            if(workouts.isEmpty()) view.tv_workoutlist_empty.visibility = View.VISIBLE
            else view.tv_workoutlist_empty.visibility = View.INVISIBLE
        })

        when(main.selectedDate){
            LocalDate.now() -> {
                view.workoutlist_date.text = "Ma"
            }
            LocalDate.now().plusDays(1L)->{
                view.workoutlist_date.text = "Holnap"
            }
            LocalDate.now().minusDays(1L)->{
                view.workoutlist_date.text = "Tegnap"
            }
            else ->{
                view.workoutlist_date.text = main.selectedDate.format(DateTimeFormatter.ofPattern("YYYY.MM.dd"))
            }
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
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
        itemTouchHelper.attachToRecyclerView(view.rvWorkouts)

        view.workoutlist_date_previous.setOnClickListener {

            main.selectedDate = main.selectedDate.minusDays(1L)
            workoutViewModel.getWorkoutsByDate(java.sql.Date.valueOf(main.selectedDate.toString())).observe(viewLifecycleOwner, Observer { workouts ->
                adapter.setData(workouts)
                if(workouts.isEmpty()) view.tv_workoutlist_empty.visibility = View.VISIBLE
                else view.tv_workoutlist_empty.visibility = View.INVISIBLE
            })
            when(main.selectedDate){
                LocalDate.now() -> {
                    view.workoutlist_date.text = "Ma"
                }
                LocalDate.now().plusDays(1L)->{
                    view.workoutlist_date.text = "Holnap"
                }
                LocalDate.now().minusDays(1L)->{
                    view.workoutlist_date.text = "Tegnap"
                }
                else ->{
                    view.workoutlist_date.text = main.selectedDate.format(DateTimeFormatter.ofPattern("YYYY.MM.dd"))
                }
            }
        }

        view.workoutlist_date_next.setOnClickListener {
            main.selectedDate = main.selectedDate.plusDays(1L)
            workoutViewModel.getWorkoutsByDate(java.sql.Date.valueOf(main.selectedDate.toString())).observe(viewLifecycleOwner, Observer { workouts ->
                adapter.setData(workouts)
                if(workouts.isEmpty()) view.tv_workoutlist_empty.visibility = View.VISIBLE
                else view.tv_workoutlist_empty.visibility = View.INVISIBLE
            })
            when(main.selectedDate){
                LocalDate.now() -> {
                    view.workoutlist_date.text = "Ma"
                }
                LocalDate.now().plusDays(1L)->{
                    view.workoutlist_date.text = "Holnap"
                }
                LocalDate.now().minusDays(1L)->{
                    view.workoutlist_date.text = "Tegnap"
                }
                else ->{
                    view.workoutlist_date.text = main.selectedDate.format(DateTimeFormatter.ofPattern("YYYY.MM.dd"))
                }
            }
        }

        view.fabAddWorkout.setOnClickListener{
            findNavController().navigate(R.id.action_workoutListFragment_to_addWorkoutFragment)
        }

        view.rvWorkouts.adapter = adapter
        return view

    }

    //onClick/onTouch implementáció amit a bindolt view-k használnak
    override fun myOnClickListener(workout: Workout) {
        //actionon keresztül adjuk meg a célt és az argumentumokat
        val action = WorkoutListFragmentDirections.actionWorkoutListFragmentToWorkoutExerciseListFragment(workout.workoutId)
        findNavController().navigate(action)
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