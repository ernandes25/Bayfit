package com.baysoftware.bayfit.running.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewHistory.setOnClickListener { //Ao invés de buttonCOnfig, clicar sobre o item da lista
            // coloquei recyclerView.Ver com Higor
            findNavController().navigate(R.id.action_fragment_history_list_to_fragment_exercise_report)
        }                                       //falta criar a fragment exerciceReportFragment

    }
}


