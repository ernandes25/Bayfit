package com.baysoftware.bayfit.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.databinding.FragmentHomeBinding
import com.baysoftware.bayfit.db.ExerciseSessionEntity
import com.baysoftware.bayfit.history.view.HistoryActivity
import com.baysoftware.bayfit.history.view.HistoryListViewModel
import com.baysoftware.bayfit.running.view.RunningActivity
import java.time.LocalDate
import java.time.LocalTime

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    //  private val viewModel: HistoryListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonConfig.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_settings)
        }
        binding.imageButtonIniciar.setOnClickListener {
            val intent = Intent(requireContext(), RunningActivity::class.java)
            startActivity(intent)
        }

        binding.buttonHistory.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}