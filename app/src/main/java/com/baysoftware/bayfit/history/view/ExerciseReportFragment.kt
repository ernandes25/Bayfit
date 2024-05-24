package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.baysoftware.bayfit.R


/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseReportFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_report, container, false)
    }
}