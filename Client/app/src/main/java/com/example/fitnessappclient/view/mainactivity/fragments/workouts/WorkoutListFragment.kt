package com.example.fitnessappclient.view.mainactivity.fragments.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
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
import com.example.fitnessappclient.view.mainactivity.fragments.plans.PlanFragment
import com.example.fitnessappclient.view.mainactivity.fragments.profile.ProfileFragment
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_workout_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class WorkoutListFragment : Fragment(), WorkoutRecyclerViewListener {

    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var itemTouchHelper: ItemTouchHelper

    //prepare view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workout_list, container, false)

        val main = activity as MainActivity

        //WorokutViewModel init
        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        //recyclerviewinit, mindig kell, ha dátumot változtatunk, notifyDataChange-re nem bindolja újra az elemeket
        val adapter = addRecyclerViewAdapter(view,main,true)


        setDate(view, main)

        //Button init
        initButtons(view,main)

        return view

    }

    //RecyclerViewAdapter, new instance, new data with selectedDate


    private fun addRecyclerViewAdapter(view : View, main : MainActivity, isInit: Boolean) : WorkoutAdapter{

        //RecyclerView init
        val adapter = WorkoutAdapter(this)
        view.rv_workoutlist.layoutManager = LinearLayoutManager(requireContext())

        workoutViewModel.getWorkoutsByDate(java.sql.Date.valueOf(main.selectedDate.toString())).observe(viewLifecycleOwner, Observer { workouts ->
            adapter.setData(workouts)
            if(workouts.isEmpty()) view.tv_workoutlist_empty.visibility = View.VISIBLE
            else view.tv_workoutlist_empty.visibility = View.INVISIBLE
        })
        //ha már rá van csatlakoztatva egy listára, akkor először le kell szedni róla és csak utána
        // szabad újat rakni rá különben, az előző tömböt próbálja változtatni
        if(isInit) {
            val itemTouchHelperCallback = createItemTouchHelper(adapter)
            itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(view.rv_workoutlist)
        }
        else{
            itemTouchHelper.attachToRecyclerView(null)
            val itemTouchHelperCallback = createItemTouchHelper(adapter)
            itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(view.rv_workoutlist)
        }
        view.rv_workoutlist.adapter = adapter

        return adapter

    }

    //swipe to delete for recyclerview
    private fun createItemTouchHelper(adapter: WorkoutAdapter) : ItemTouchHelper.SimpleCallback{
        return object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeAt(viewHolder.adapterPosition, workoutViewModel)
            }
        }
    }

    //Set Date Text
    private fun setDate(view: View, main: MainActivity){
        when(main.selectedDate){
            LocalDate.now() -> {
                view.tv_workoutlist_date.text = requireContext().resources.getString(R.string.today)
            }
            LocalDate.now().plusDays(1L)->{
                view.tv_workoutlist_date.text = requireContext().resources.getString(R.string.tomorrow)
            }
            LocalDate.now().minusDays(1L)->{
                view.tv_workoutlist_date.text = requireContext().resources.getString(R.string.yesterday)
            }
            else ->{
                view.tv_workoutlist_date.text = main.selectedDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            }
        }
    }

    private fun initButtons(view : View, main: MainActivity){
        view.ibtn_workoutlist_previousdate.setOnClickListener {
            main.selectedDate = main.selectedDate.minusDays(1L)
            addRecyclerViewAdapter(view,main,false)
            setDate(view,main)
        }

        view.ibtn_workoutlist_nextdate.setOnClickListener {
            main.selectedDate = main.selectedDate.plusDays(1L)
            addRecyclerViewAdapter(view,main,false)
            setDate(view,main)
        }

        view.fab_workoutlist_addworkout.setOnClickListener{
            findNavController().navigate(R.id.action_workoutListFragment_to_addWorkoutFragment)
        }

        view.bnv_workoutlist_navmenu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home ->{
                }
                R.id.exercises ->{
                    findNavController().navigate(R.id.action_workoutListFragment_to_planFragment)
                }
                R.id.profile -> {
                    findNavController().navigate(R.id.action_workoutListFragment_to_profileFragment)
                }
                else ->{}
            }
            true
        }
    }



    //
    //onClick/onTouch implementáció amit a bindolt view-k használnak
    //
    override fun myOnClickListener(workout: Workout) {
        //actionon keresztül adjuk meg a célt és az argumentumokat
        val action = WorkoutListFragmentDirections.actionWorkoutListFragmentToWorkoutExerciseListFragment(workout.workoutId)
        findNavController().navigate(action)
    }

    //item háttérszín állítása érintés esetén
    override fun myOnTouchListener(view: View, motionevent: MotionEvent) : Boolean {
        //TODO: ha kéne
       return false
    }
}