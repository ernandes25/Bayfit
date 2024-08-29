package com.baysoftware.bayfit.running.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.core.content.getSystemService
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.databinding.FragmentTimerBinding
import com.baysoftware.bayfit.db.ExerciseSessionEntity
import com.baysoftware.bayfit.preferences.UserManager
import com.baysoftware.bayfit.running.service.DecreasingTimerService
import com.baysoftware.bayfit.running.service.IncreasingTimerService
import com.baysoftware.bayfit.running.service.TimerService
import com.baysoftware.bayfit.running.service.TotalTimerService
import com.baysoftware.bayfit.util.getTimeStringFromDouble
import com.baysoftware.bayfit.viewmodel.TimerViewModel
import com.baysoftware.bayfit.viewmodel.TimerViewModelFactory
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset

class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    private lateinit var totalTimerServiceIntent: Intent
    private lateinit var restTimerServiceIntent: Intent
    private lateinit var timerMode: UserManager.TimerMode
    private val viewModel: TimerViewModel by viewModels {
        TimerViewModelFactory(requireActivity().application)
    }

    private var totalRestTime = 0.00
    private var restTime = 0.00
    private var increasingTime = 0.00
    private var isResting = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_timer, container, false)

        // Iniciando contador de tempo total
        totalTimerServiceIntent = Intent(requireActivity(), TotalTimerService::class.java)
        totalTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, increasingTime)
        registerReceiver(
            requireContext(),
            updateTotalTimerBroadcastReceiver,
            IntentFilter(TotalTimerService.TIMER_UPDATE),
            RECEIVER_NOT_EXPORTED
        )
        requireActivity().startService(totalTimerServiceIntent)

        // Iniciando contador de tempo de descanso
        lifecycleScope.launch {
            timerMode = UserManager.getInstance().readTimerMode(requireContext())
            when (timerMode) {
                UserManager.TimerMode.FREE,
                UserManager.TimerMode.UNDEFINED -> {
                    // Se o tempo for livre, o intent tem que ser configurado para que o serviço seja
                    // CRESCENTE, portanto não há necessidade de consultar o DataStore para obter tempo
                    // informado pelo usuário.
                    restTimerServiceIntent =
                        Intent(requireContext(), IncreasingTimerService::class.java)
                    restTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, 0.0)
                }

                UserManager.TimerMode.PREDEFINED -> {
                    // Se o tempo for pré-definido, o intent tem que ser configurado para que o serviço
                    // seja DECRESCENTE, e o valor padrão deve ser obtido do DataStore, que por sua vez
                    // foi informado pelo usuário.
                    val timerConfiguration =
                        UserManager.getInstance().readTimerConfiguration(requireContext())
                    restTime =
                        timerConfiguration.minute.toDouble() * 60 + timerConfiguration.second.toDouble()
                    restTimerServiceIntent =
                        Intent(requireContext(), DecreasingTimerService::class.java)
                    restTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, restTime)
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resumeButton.setOnLongClickListener {
            stopTrainingSession()
            saveExerciseSession()
            navigateToResultScreen()
            true
        }

        binding.pauseButton.setOnClickListener { pauseTimer() }
        binding.resumeButton.setOnClickListener { resumeTraining() }
    }

    private fun saveExerciseSession() {
        val newSession = ExerciseSessionEntity(
            id = 0, // Auto-geração de ID pelo banco de dados
            date = LocalDate.now(),
            startTime = LocalTime.now().minusMinutes(30), // Exemplo de hora de início
            endTime = LocalTime.now(), // Hora de término atual
            totalTime = increasingTime.toInt(), // Tempo total em segundos
            restTime = totalRestTime.toInt() // Tempo de descanso em segundos
        )
        viewModel.insertSession(newSession)
    }

    private fun stopTrainingSession() {
        requireActivity().stopService(totalTimerServiceIntent)
        requireActivity().stopService(restTimerServiceIntent)
        requireActivity().unregisterReceiver(updateTotalTimerBroadcastReceiver)
        requireActivity().unregisterReceiver(updateRestTimerBroadcastReceiver)
    }

    private fun navigateToResultScreen() {
        val bundle = bundleOf(
            "endTime" to binding.secondaryTimer.text,
            "endRest" to totalRestTime
        )
        findNavController().navigate(R.id.action_timer_fragment_to_fragment_result, bundle)
    }

    private fun vibrate(duration: Long = 500) {
        val vibrator = requireContext().getSystemService() as? Vibrator
        vibrator?.vibrate(
            VibrationEffect.createOneShot(
                duration,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }

    private fun pauseTimer() {
        isResting = true
        if (timerMode == UserManager.TimerMode.FREE) {
            startRestTimer(IncreasingTimerService.TIMER_UPDATE)

            // Atualizações de visualização
            binding.pauseButton.isInvisible = true
            binding.resumeButton.isInvisible = false
            binding.primaryTimer.setTextColor(resources.getColor(R.color.green, null))
            binding.secondaryTimer.isInvisible = false
            binding.secondaryTimer.text = binding.primaryTimer.text
        } else {
            startRestTimer(DecreasingTimerService.TIMER_UPDATE)

            // Atualizações de visualização
            binding.textRest.isInvisible = false
            binding.pauseButton.isInvisible = true
            binding.primaryTimer.setTextColor(resources.getColor(R.color.green, null))
            binding.secondaryTimer.isInvisible = false
            binding.secondaryTimer.text = binding.primaryTimer.text
        }
    }

    private fun resumeTraining() {
        isResting = false
        requireActivity().stopService(restTimerServiceIntent)
        binding.resumeButton.isInvisible = true
        binding.pauseButton.isInvisible = false
        binding.primaryTimer.setTextColor(resources.getColor(R.color.white, null))
        binding.primaryTimer.text = binding.secondaryTimer.text
        binding.secondaryTimer.isInvisible = true
    }

    private fun startRestTimer(timerService: String) {
        registerReceiver(
            requireContext(),
            updateRestTimerBroadcastReceiver,
            IntentFilter(timerService),
            RECEIVER_NOT_EXPORTED
        )
        requireActivity().startService(restTimerServiceIntent)
    }

    private fun stopTimer() {
        requireActivity().stopService(restTimerServiceIntent)
        isResting = true
    }

    private val updateTotalTimerBroadcastReceiver: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                increasingTime = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
                val time = increasingTime.getTimeStringFromDouble()
                if (isResting) {
                    binding.secondaryTimer.text = time
                } else {
                    binding.primaryTimer.text = time
                }
            }
        }

    private val updateRestTimerBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)

            if (timerMode == UserManager.TimerMode.PREDEFINED && time == 0.00) {
                stopTimer()
                binding.resumeButton.isInvisible = false
                binding.textRest.isInvisible = true
                vibrate()
            }
            totalRestTime++

            binding.primaryTimer.text = time.getTimeStringFromDouble()
        }

    }
}
