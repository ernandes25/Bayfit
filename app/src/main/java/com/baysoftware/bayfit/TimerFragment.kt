package com.baysoftware.bayfit

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
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.baysoftware.bayfit.databinding.FragmentTimerBinding
import kotlin.math.roundToInt

class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    private lateinit var increasingTimerServiceIntent: Intent
    private lateinit var decreasingTimerServiceIntent: Intent
    private var increasingTime = 0.00
    private var decreasingTime = 5.00
    private var timerStarted = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_timer, container, false)
        increasingTimerServiceIntent = Intent(requireContext(), IncreasingTimerService::class.java)
        increasingTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, increasingTime)
        decreasingTimerServiceIntent = Intent(requireContext(), DecreasingTimerService::class.java)
        decreasingTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, decreasingTime)

        registerReceiver(
            requireContext(),
            updateIncreasingTime,
            IntentFilter(IncreasingTimerService.TIMER_UPDATE),
            RECEIVER_NOT_EXPORTED
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pauseButton.setOnClickListener { pauseTimer() }
        binding.resumeButton.setOnClickListener { resumeTraining() }
        //TODO: Implementar navegação para tela final usando a alinha baixo.
        // binding.controlButtonRest.setOnLongClickListener { resetTimer() }

        requireActivity().startService(increasingTimerServiceIntent)
    }

    private fun Fragment.vibrate(duration: Long = 500) {
        val vibrator = requireContext().getSystemService() as? Vibrator
        vibrator?.vibrate(
            VibrationEffect.createOneShot(
                duration,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }

    private fun pauseTimer() {
        timerStarted = false
        startDecreasingTimer()
        binding.textRest.isInvisible = false
        binding.pauseButton.isInvisible = true
        binding.primaryTimer.setTextColor(resources.getColor(R.color.green, null))
        binding.secondaryTimer.isInvisible = false
        binding.secondaryTimer.text = binding.primaryTimer.text
    }

    private fun resumeTraining() {
        (timerStarted) = true
        requireActivity().stopService(decreasingTimerServiceIntent)
        binding.resumeButton.isInvisible = true
        binding.pauseButton.isInvisible = false
        binding.primaryTimer.setTextColor(resources.getColor(R.color.white, null))
        binding.primaryTimer.text = binding.secondaryTimer.text
        binding.secondaryTimer.isInvisible = true
    }

    private fun startDecreasingTimer() {
        registerReceiver(
            requireContext(),
            updateDecreasingTime,
            IntentFilter(DecreasingTimerService.TIMER_UPDATE),
            RECEIVER_NOT_EXPORTED
        )
        requireActivity().startService(decreasingTimerServiceIntent)
    }

    // TODO: este método será utilizado quando o usuário finalizar o treino
    //  @Suppress("unused")
    private fun stopTimer() {

        requireActivity().stopService(decreasingTimerServiceIntent)
        timerStarted = false
    }

    private val updateIncreasingTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            increasingTime = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            val time = getTimeStringFromDouble(increasingTime)
            if (timerStarted) {
                binding.primaryTimer.text = time
            } else {
                binding.secondaryTimer.text = time
            }
        }
    }

    private val updateDecreasingTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            decreasingTime = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            if (decreasingTime == 0.0) {
                stopTimer()
                binding.primaryTimer.text
                binding.resumeButton.isInvisible = false
                binding.textRest.isInvisible = true
                vibrate()

                // TODO: parar tempo (chamar métodos "stopService" e possivelmente "unregisterReceiver")
                // TODO: esconder "Descanso"
                // TODO: mostrar botão play verde
            }
            binding.primaryTimer.text = getTimeStringFromDouble(decreasingTime)
        }
    }

    // TODO: colocar este método em arquivo de extension functions
    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % TOTAL_MINUTES_IN_DAY / TOTAL_MINUTES_IN_HOUR
        val minutes = resultInt % TOTAL_MINUTES_IN_DAY % TOTAL_MINUTES_IN_HOUR / TOTAL_HOURS
        val seconds = resultInt % TOTAL_MINUTES_IN_DAY % TOTAL_MINUTES_IN_HOUR % TOTAL_HOURS
        return makeTimeString(hours, minutes, seconds)
    }

    // TODO: colocar este método em arquivo de extension functions
    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)

    companion object {
        const val TOTAL_MINUTES_IN_DAY = 86400
        const val TOTAL_MINUTES_IN_HOUR = 3600
        const val TOTAL_HOURS = 60
    }
}