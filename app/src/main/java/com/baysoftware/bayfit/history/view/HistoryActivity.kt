package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.baysoftware.bayfit.databinding.ActivityHistoryBinding
import com.baysoftware.bayfit.db.ExerciseSessionEntity
import com.baysoftware.bayfit.history.view.adapter.ExerciseSessionAdapter


class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryListViewModel by viewModels()
    private var sessions: MutableList<ExerciseSessionEntity> = mutableListOf()
    private lateinit var adapter: ExerciseSessionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(this)
        adapter = ExerciseSessionAdapter(sessions) { session ->
            // Handle item click
        }
        binding.recyclerViewHistory.adapter = adapter
        updateViewVisibility()
    }

    private fun observeViewModel() {
        viewModel.exerciseSessions.observe(this) { newSessions ->
            sessions.clear()
            sessions.addAll(newSessions)
            adapter.notifyDataSetChanged()
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
