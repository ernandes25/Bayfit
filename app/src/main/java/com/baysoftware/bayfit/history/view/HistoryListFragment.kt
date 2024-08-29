package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.baysoftware.bayfit.databinding.FragmentHistoryListBinding

class HistoryListFragment : Fragment() {

    private lateinit var binding: FragmentHistoryListBinding
    private lateinit var adapter: ExerciseSessionAdapter
    private val viewModel: HistoryListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.allSessions.observe(viewLifecycleOwner) { sessions ->
            adapter.updateSessions(sessions)
            updateViewVisibility()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
        adapter = ExerciseSessionAdapter { session ->
            // TODO > navigate
        }
        binding.recyclerViewHistory.adapter = adapter
    }

    private fun updateViewVisibility() {
        if (adapter.itemCount <= 0) {
            binding.recyclerViewHistory.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.recyclerViewHistory.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }
    }
}
