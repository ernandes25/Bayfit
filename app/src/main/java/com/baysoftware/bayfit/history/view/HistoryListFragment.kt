package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.baysoftware.bayfit.databinding.FragmentHistoryListBinding
import com.baysoftware.bayfit.db.ExerciseSessionEntity
import com.baysoftware.bayfit.history.view.adapter.ExerciseSessionAdapter

//import com.baysoftware.bayfit.viewmodel.HistoryListViewModel

class HistoryListFragment : Fragment() {
    private lateinit var adapter: ExerciseSessionAdapter
    private lateinit var binding: FragmentHistoryListBinding
    private val viewModel: HistoryListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(context)
        adapter = ExerciseSessionAdapter(emptyList()) { session ->
            navigateToDetail(session)
        }
        binding.recyclerViewHistory.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.allSessions.observe(viewLifecycleOwner) { newSessions ->
            adapter.updateSessions(newSessions)
            updateViewVisibility(newSessions)
        }
    }

    private fun updateViewVisibility(sessions: List<ExerciseSessionEntity>) {
        if (sessions.isEmpty()) {
            binding.recyclerViewHistory.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.recyclerViewHistory.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }
    }

    private fun navigateToDetail(session: ExerciseSessionEntity) {
        val action =
            HistoryListFragmentDirections.actionHistoryListFragmentToExerciseReportFragment(session)
        findNavController().navigate(action)
    }
}