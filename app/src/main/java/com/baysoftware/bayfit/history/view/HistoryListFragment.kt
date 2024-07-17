package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.baysoftware.bayfit.databinding.FragmentHistoryListBinding
import com.baysoftware.bayfit.db.ExerciseSession
import java.time.LocalDate
import java.time.LocalTime

class HistoryListFragment : Fragment() {
    private lateinit var adapter: ExerciseSessionAdapter
    private lateinit var binding: FragmentHistoryListBinding

    private val sessions = listOf(
        ExerciseSession(LocalDate.of(2024, 5, 25), LocalTime.of(1, 30, 0)),
        ExerciseSession(LocalDate.of(2024, 5, 24), LocalTime.of(1, 30, 0)),
        ExerciseSession(LocalDate.of(2024, 5, 23), LocalTime.of(1, 30, 0)),
        ExerciseSession(LocalDate.of(2024, 5, 25), LocalTime.of(1, 30, 0)),
        ExerciseSession(LocalDate.of(2024, 5, 24), LocalTime.of(1, 30, 0)),
        ExerciseSession(LocalDate.of(2024, 5, 23), LocalTime.of(1, 30, 0)),
        ExerciseSession(LocalDate.of(2024, 5, 25), LocalTime.of(1, 30, 0)),
        ExerciseSession(LocalDate.of(2024, 5, 24), LocalTime.of(1, 30, 0)),
        ExerciseSession(LocalDate.of(2024, 5, 23), LocalTime.of(1, 30, 0)),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHistoryListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }
    private fun setupRecyclerView() {
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(context)
        adapter = ExerciseSessionAdapter(requireContext(), sessions) { session ->
        }

        binding.recyclerViewHistory.adapter = adapter
        updateViewVisibility()
    }

    private fun updateViewVisibility() {
        if (sessions.isEmpty()) {
            binding.recyclerViewHistory.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.recyclerViewHistory.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }
    }
}