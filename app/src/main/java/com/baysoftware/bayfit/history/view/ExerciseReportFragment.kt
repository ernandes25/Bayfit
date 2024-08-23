package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.baysoftware.bayfit.databinding.FragmentExerciseReportBinding
import com.baysoftware.bayfit.util.getTimeStringFromDouble

import java.time.format.DateTimeFormatter

class ExerciseReportFragment : Fragment() {

    private var _binding: FragmentExerciseReportBinding? = null
    private val binding get() = _binding!!

    private val args: ExerciseReportFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displaySessionDetails()
    }

    private fun displaySessionDetails() {
        val session = args.session
        binding.date.text = session.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        binding.timeTotal.text = session.totalTime.toDouble().getTimeStringFromDouble()
        binding.timeRestTotal.text = session.restTime.toDouble().getTimeStringFromDouble()
        binding.trainingStart.text = session.startTime.toString()
        binding.endTraining.text = session.endTime.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}