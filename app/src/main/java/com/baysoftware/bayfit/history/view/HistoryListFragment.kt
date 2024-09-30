package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.databinding.FragmentHistoryListBinding

class HistoryListFragment : Fragment() {

    private lateinit var binding: FragmentHistoryListBinding
    private lateinit var adapter: ExerciseSessionAdapter
    private val viewModel: HistoryListViewModel by activityViewModels {
        HistoryListViewModel.provideFactory(requireActivity().application, this)
    }

    // region Lifecycle methods

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.exerciseSessions.observe(viewLifecycleOwner) { sessions ->
            adapter.updateSessions(sessions)
            updateViewVisibility()
        }
    }

    // endregion

    // region Custom methods

    /**
     * Inicializa o RecyclerView e o Adapter, tal qual a ação a ser feita quando um item do adapter
     * for clicado (navegar para o fragmento de relatório de sessão de exercício, passando o ID da
     * sessão do exercício)
     */
    private fun setupRecyclerView() {
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(context)
        adapter = ExerciseSessionAdapter { session ->
            val bundle = Bundle()
            bundle.putLong("sessionId", session.id)
            findNavController().navigate(R.id.fragment_exercise_report, bundle)
        }
        binding.recyclerViewHistory.adapter = adapter
    }

    /**
     * Atualiza a visibilidade dos elementos da tela de acordo com a quantidade de itens no adapter.
     */
    private fun updateViewVisibility() {
        if (adapter.itemCount <= 0) {
            binding.recyclerViewHistory.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.recyclerViewHistory.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }
    }

    // endregion
}
