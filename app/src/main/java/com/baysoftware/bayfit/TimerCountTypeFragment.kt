package com.baysoftware.bayfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.baysoftware.bayfit.databinding.FragmentTimerCountTypeBinding
import kotlinx.coroutines.launch

class TimerCountTypeFragment : Fragment() {

    private lateinit var binding: FragmentTimerCountTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_timer_count_type,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val restType = UserManager.getInstance().readTimerMode(requireContext())

            if (restType == UserManager.TimerMode.FREE) {
                binding.radioButton1.isChecked = true
            } else if(restType == UserManager.TimerMode.PREDEFINED) {
                binding.radioButton2.isChecked = true
            }
        }

        lifecycleScope.launch {
            val button1 = UserManager.getInstance().readTimerMode(requireContext())

            if (button1 == UserManager.TimerMode.FREE) {
                binding.radioButton1.isChecked = true
            } else {
                binding.radioButton2.isChecked = true
            }
        }

        binding.buttonOkcountType.setOnClickListener {

            lifecycleScope.launch {
                val selectedButton = if (binding.radioButton1.isChecked) {
                    findNavController().navigate(R.id.fragment_home)
                    UserManager.TimerMode.FREE

                } else if (binding.radioButton2.isChecked) {
                    findNavController().navigate(R.id.fragment_timer_setter)
                    UserManager.TimerMode.PREDEFINED
                } else {
                    //TODO: Mostrar Toast "Selecione uma opção" e impedir navegação para a tela anterior
                    UserManager.TimerMode.UNDEFINED
                }
                UserManager.getInstance().saveTimerMode(requireContext(), selectedButton)
            }
        }
    }
}