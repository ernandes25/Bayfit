package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.baysoftware.bayfit.databinding.FragmentExerciseReportBinding
import com.baysoftware.bayfit.history.model.ExerciseSessionModel
import com.baysoftware.bayfit.util.getTimeStringFromDouble
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ExerciseReportFragment : Fragment() {

    private lateinit var binding: FragmentExerciseReportBinding
    private val viewModel: HistoryListViewModel by activityViewModels {
        HistoryListViewModel.provideFactory(requireActivity().application, this)
    }

    // region Lifecycle methods

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sessionId = arguments?.getLong("sessionId") ?: 0
//        if (sessionId > 0) {
//            val session = viewModel.getSessionById(sessionId)
//            displaySessionDetails(session)
//        }
    }

    // endregion

    // region Custom methods

    private fun displaySessionDetails(session: ExerciseSessionModel) {
        val formattedDate: String = try {
            val date = Date(session.date as Long) // Converte timestamp para Date
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
        } catch (e: Exception) {
            "Data inválida"
        }

        binding.date.text = formattedDate
        binding.timeTotal.text = session.duration.toDouble().getTimeStringFromDouble()
        binding.timeRestTotal.text = session.restTime.toDouble().getTimeStringFromDouble()
    }

    // endregion

}