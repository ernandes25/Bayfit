package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.baysoftware.bayfit.databinding.FragmentExerciseReportBinding
import com.baysoftware.bayfit.util.getTimeStringFromDouble
import java.text.SimpleDateFormat
import java.util.*

class ExerciseReportFragment : Fragment() {

    private var _binding: FragmentExerciseReportBinding? = null
    private val binding get() = _binding!!

    private val args: ExerciseReportFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displaySessionDetails()
    }

    private fun displaySessionDetails() {
        val session = args.session

        val formattedDate: String = try {
            val date = Date(session.date as Long) // Converte timestamp para Date
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
        } catch (e: Exception) {
            "Data inválida"
        }

        binding.date.text = formattedDate
        binding.timeTotal.text = session.totalTime.toDouble().getTimeStringFromDouble()
        binding.timeRestTotal.text = session.restTime.toDouble().getTimeStringFromDouble()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
