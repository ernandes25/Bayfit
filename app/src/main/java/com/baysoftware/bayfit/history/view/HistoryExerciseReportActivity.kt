package com.baysoftware.bayfit.history.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.baysoftware.bayfit.databinding.ActivityHistoryExerciseReportBinding
import com.baysoftware.bayfit.history.model.ExerciseSessionModel
import com.baysoftware.bayfit.util.getTimeStringFromDouble
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val SESSION_ID = "sessionId"

class HistoryExerciseReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryExerciseReportBinding
//    private val viewModel: HistoryListViewModel by activityViewModels { // TODO: fix view model
//        HistoryListViewModel.provideFactory(requireActivity().application, this)
//    }

    // region Lifecycle methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryExerciseReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val sessionId = intent.getLongExtra(SESSION_ID, 0)
//        if (sessionId > 0) {
//            val session = viewModel.getSessionById(sessionId)
//            displaySessionDetails(session)
//        }
        return super.onCreateView(name, context, attrs)
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