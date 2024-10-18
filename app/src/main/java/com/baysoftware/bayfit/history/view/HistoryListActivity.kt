package com.baysoftware.bayfit.history.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.baysoftware.bayfit.databinding.ActivityHistoryListBinding

class HistoryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryListBinding
    private lateinit var adapter: ExerciseSessionAdapter
    private val viewModel: HistoryListViewModel by viewModels<HistoryListViewModel> {
        HistoryListViewModel.provideFactory(this.application, this)
    }

    // region Lifecycle methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        viewModel.exerciseSessions.observe(this) { sessions ->
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
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(this)
        adapter = ExerciseSessionAdapter { session ->
            val bundle = Bundle()
            bundle.putLong("sessionId", session.id)
            val intent = Intent(this, HistoryExerciseReportActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
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
