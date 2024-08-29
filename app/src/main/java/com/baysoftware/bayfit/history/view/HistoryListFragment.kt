package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.baysoftware.bayfit.databinding.FragmentHistoryListBinding
import com.baysoftware.bayfit.db.ExerciseSessionEntity
import com.baysoftware.bayfit.history.view.adapter.ExerciseSessionAdapter

class HistoryListFragment : Fragment() {

    private var _binding: FragmentHistoryListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HistoryListViewModel
    private lateinit var adapter: ExerciseSessionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        _binding = FragmentHistoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HistoryListFragment", "Antes da configuração do Adapter")
        viewModel = ViewModelProvider(this).get(HistoryListViewModel::class.java)

        adapter = ExerciseSessionAdapter(emptyList()) { session ->
            navigateToDetail(session)
        }
        Log.d("HistoryListFragment", "Adapter configurado")
        binding.recyclerViewHistory.adapter = adapter

        // Observando as sessões de exercício
        viewModel.exerciseSessions.observe(viewLifecycleOwner) { sessions ->

            Log.d("HistoryListFragment", "Dados observados: ${sessions.size} sessões")
            adapter.updateSessions(sessions)
        }
    }

    private fun navigateToDetail(session: ExerciseSessionEntity) {
        val action =
            HistoryListFragmentDirections.actionHistoryListFragmentToExerciseReportFragment(session)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}