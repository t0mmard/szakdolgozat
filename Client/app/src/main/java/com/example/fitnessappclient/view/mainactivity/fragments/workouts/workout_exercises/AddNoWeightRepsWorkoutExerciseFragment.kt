package com.example.fitnessappclient.view.mainactivity.fragments.workouts.workout_exercises

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
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
import com.example.fitnessappclient.repository.entities.MySet
import com.example.fitnessappclient.view.mainactivity.MainActivity
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
            val action =
                AddNoWeightRepsWorkoutExerciseFragmentDirections.actionAddNoWeightRepsWorkoutExerciseFragmentToWorkoutExerciseListFragment(
                    args.workoutId
                )
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
            val parentActivity = activity as MainActivity
            val set = MySet(
                0,
                args.workoutExerciseId,
                null,
                (adapter.itemCount + 1 ).toShort(),
                etNumberOfReps.text.toString().toShort(),
                0,
                parentActivity.currentUser
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
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(view.rvSets)


        return view
    }


}