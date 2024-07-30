package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.baysoftware.bayfit.databinding.FragmentHistoryListBinding
import com.baysoftware.bayfit.db.ExerciseSession

class HistoryListFragment : Fragment() {
    private lateinit var adapter: ExerciseSessionAdapter
    private lateinit var binding: FragmentHistoryListBinding
    private val viewModel: HistoryListViewModel by viewModels()
    private lateinit var sessions: MutableList<ExerciseSession>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }
    private fun setupRecyclerView() {
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(context)
        adapter = ExerciseSessionAdapter(requireContext(),  emptyList()) { session ->
        }
        binding.recyclerViewHistory.adapter = adapter
        updateViewVisibility()
        // Observe the LiveData from the ViewModel
        viewModel.allSessions.observe(viewLifecycleOwner) { sessions ->
            //adapter.updateSessions(sessions)
            this.sessions.addAll(sessions)
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