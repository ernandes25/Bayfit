package com.baysoftware.bayfit.running.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.databinding.FragmentHistoryListBinding
import com.baysoftware.bayfit.home.view.ExerciseSessionAdapter

class HistoryListFragment : Fragment() {

    private lateinit var binding: FragmentHistoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_history_list,
            container,
            false
        )
//        TODO: Implementar código abaixo após criação do ADAPTER (Próxima tarefa-Ver board github)
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val recyclerView = binding.recyclerViewHistory

        val adapter = ExerciseSessionAdapter()
        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(this.context )

        return binding.root
    }


}