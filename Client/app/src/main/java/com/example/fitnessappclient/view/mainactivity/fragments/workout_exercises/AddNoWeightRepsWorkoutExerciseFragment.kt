package com.example.fitnessappclient.view.mainactivity.fragments.workout_exercises

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessappclient.R
import com.example.fitnessappclient.repository.entities.ExerciseType
import com.example.fitnessappclient.repository.entities.MySet
import com.example.fitnessappclient.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_add_no_weight_reps_workout_exercise.*
import kotlinx.android.synthetic.main.fragment_add_no_weight_reps_workout_exercise.view.*
import kotlinx.android.synthetic.main.fragment_workout_list.view.*


class AddNoWeightRepsWorkoutExerciseFragment : Fragment() {
//    var ratio : Double = 1.0
//    var cornerAnimationBottom : ValueAnimator? = null
//    var cornerAnimationTop : ValueAnimator? = null

    private lateinit var workoutViewModel : WorkoutViewModel
    val args by navArgs<AddNoWeightRepsWorkoutExerciseFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_no_weight_reps_workout_exercise, container, false)

        //RecyclerView
        val adapter = SetsAdapter(ExerciseType.REPETITION)
        view.rvSets.adapter = adapter
        view.rvSets.layoutManager = LinearLayoutManager(requireContext())

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        workoutViewModel.getAllSetsByWorkoutExerciseId(args.workoutExerciseId).observe(viewLifecycleOwner, Observer { sets ->
            adapter.setData(sets)
            view.rvSets.scrollToPosition(adapter.itemCount-1)
        })


        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = AddNoWeightRepsWorkoutExerciseFragmentDirections.actionAddNoWeightRepsWorkoutExerciseFragmentToWorkoutExerciseListFragment(args.workoutId)
            workoutViewModel.updateUserComment(args.workoutExerciseId, view.etClientComment.text.toString())
            findNavController().navigate(action)
        }

        view.btn_reps_minus.setOnClickListener {
            var currentNumber = view.etNumberOfReps.text.toString().toInt()
            if(currentNumber>0) {
                if (currentNumber == 1) view.btn_reps_minus.isEnabled = false
                currentNumber--
                view.etNumberOfReps.text =
                    Editable.Factory.getInstance().newEditable(currentNumber.toString())
            }
        }

        view.btn_reps_plus.setOnClickListener {
            var currentNumber = view.etNumberOfReps.text.toString().toInt()
            if(currentNumber == 0) view.btn_reps_minus.isEnabled = true
            currentNumber++
            view.etNumberOfReps.text =
                Editable.Factory.getInstance().newEditable(currentNumber.toString())

        }

        view.btnAddSet.setOnClickListener {
            val set = MySet(
                0,
                args.workoutExerciseId,
                null,
                (adapter.itemCount + 1 ).toShort(),
                etNumberOfReps.text.toString().toShort(),
                0
            )
            workoutViewModel.insertSet(set)
        }

        view.etClientComment.setText(args.comment)


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
        itemTouchHelper.attachToRecyclerView(view.rvSets)

//        view.btnAddExercise.setOnTouchListener { viewBtn, event ->
//            var eventConsumed = false
//            val gd:GradientDrawable = viewBtn.background as GradientDrawable
//            val currentStartValueTop = gd.cornerRadii!![0]
//            val currentStartValueBottom = gd.cornerRadii!![4]
//            var currentEndValueTop = 0.0f
//            var currentEndValueBottom = 50.0f
//            var startColor = viewBtn.backgroundTintList
//            var endColor = ContextCompat.getColor(activity as Context, R.color.purple_500)
//            when(event.action){
//                MotionEvent.ACTION_DOWN ->{
//                    currentEndValueBottom = 100.0f
//                    currentEndValueTop = 100.0f
//                    endColor = ContextCompat.getColor(activity as Context, R.color.purple_200)
//                }
//                MotionEvent.ACTION_UP -> {
//                    eventConsumed = true
//                }
//                else ->{
//                }
//            }
//            cornerAnimationTop = ValueAnimator.ofFloat(
//                currentStartValueTop,
//                currentEndValueTop
//            )
//
//            cornerAnimationBottom = ValueAnimator.ofFloat(
//                currentStartValueBottom,
//                currentEndValueBottom
//            )
//            if (ratio > 0.98) ratio = 1.0
//            else if (ratio < 0.02) ratio = 0.0
//            val duration = 2000L * ratio
//            cornerAnimationTop!!.duration = 2000L
//            cornerAnimationBottom!!.duration = duration.toLong()
//            println("duration: $duration")
//            cornerAnimationTop!!.addUpdateListener { it1 ->
//                val value1 = it1.animatedValue as Float
//                    cornerAnimationBottom!!.addUpdateListener { it2 ->
//                        val value2 = it2.animatedValue as Float
//                        ratio = it2.currentPlayTime.toDouble() / it2.duration.toDouble()
//                        gd.cornerRadii = floatArrayOf(value1,value1,value1,value1,value2,value2,value2,value2)
//                    }
//            }
//
//            cornerAnimationTop!!.start()
//            cornerAnimationBottom!!.start()
//
//            val backgroundColorAnimator = ObjectAnimator.ofObject(
//                viewBtn,
//                "backgroundTint",
//                ArgbEvaluator(),
//                startColor,
//                endColor
//            )
//            backgroundColorAnimator.duration = 200
//            backgroundColorAnimator.start()
//            viewBtn.background = gd
//            eventConsumed
//        }



        return view
    }


}