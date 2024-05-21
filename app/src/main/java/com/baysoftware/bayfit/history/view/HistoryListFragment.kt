package com.baysoftware.bayfit.history.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.databinding.FragmentHistoryListBinding

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
//        TODO: Implementar código abaixo após criação do Repositório/Após estar funcional
        //val adapter = ExerciseSessionAdapter()
        //recyclerView.adapter = adapter
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }
}