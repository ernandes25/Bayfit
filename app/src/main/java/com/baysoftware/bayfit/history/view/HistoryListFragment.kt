package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.baysoftware.bayfit.databinding.FragmentHistoryListBinding
import com.baysoftware.bayfit.db.ExerciseSessionEntity
import com.baysoftware.bayfit.history.view.adapter.ExerciseSessionAdapter

class HistoryListFragment : Fragment() {
    private lateinit var adapter: ExerciseSessionAdapter
    private lateinit var binding: FragmentHistoryListBinding
    private val viewModel: HistoryListViewModel by viewModels()
    private var sessions: MutableList<ExerciseSessionEntity> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(context)
        adapter = ExerciseSessionAdapter(sessions) { session ->
        }
        binding.recyclerViewHistory.adapter = adapter
        updateViewVisibility()
    }

    private fun observeViewModel() {
        viewModel.allSessions.observe(viewLifecycleOwner) { newSessions ->
            Log.d("HistoryListFragment", "New sessions received: $newSessions")
            sessions.clear()
            sessions.addAll(newSessions)
            adapter.updateSessions(sessions)
            updateViewVisibility()
        }
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