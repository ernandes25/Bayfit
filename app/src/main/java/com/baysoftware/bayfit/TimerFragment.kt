package com.baysoftware.bayfit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.baysoftware.bayfit.databinding.FragmentTimerBinding
import kotlin.math.roundToInt

class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    var timerStarted = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_timer, container, false)
        serviceIntent = Intent(requireContext(), TimerService::class.java)
        registerReceiver(
            requireContext(),
            updateTime,
            IntentFilter(TimerService.TIMER_UPDATE),
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
        startStopTimer()
    }

    private fun pauseTimer() {
            binding.primaryTimer.setTextColor(resources.getColor(R.color.red, null))
            binding.primaryTimer.setTextSize(resources.getDimension(R.dimen.text_size_timer_small))
            binding.secondaryTimer.isVisible = true
            binding.resumeButton.isVisible = true
            binding.pauseButton.isVisible = false
    }

    private fun resumeTraining() {
(timerStarted)
        binding.secondaryTimer.isVisible = false
        binding.primaryTimer.textSize = resources.getDimension(R.dimen.text_size_timer2)
        binding.resumeButton.isVisible = false
        binding.pauseButton.isVisible = true
        binding.primaryTimer.setTextColor(resources.getColor(R.color.white, null))
    }

    private fun startStopTimer() {
        if (timerStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        requireActivity().startService(serviceIntent)
        binding.pauseButton.setImageResource(R.drawable.ic_pause)
        binding.pauseButton.isVisible = true
        binding.primaryTimer.setTextColor(resources.getColor(R.color.white, null))
        timerStarted = true
    }

    private fun stopTimer() {
        requireActivity().stopService(serviceIntent)
        timerStarted = false
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            binding.primaryTimer.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % TOTAL_MINUTES_IN_DAY / TOTAL_MINUTES_IN_HOUR
        val minutes = resultInt % TOTAL_MINUTES_IN_DAY % TOTAL_MINUTES_IN_HOUR / TOTAL_HOURS
        val seconds = resultInt % TOTAL_MINUTES_IN_DAY % TOTAL_MINUTES_IN_HOUR % TOTAL_HOURS
        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)

    companion object {
        const val TOTAL_MINUTES_IN_DAY = 86400
        const val TOTAL_MINUTES_IN_HOUR = 3600
        const val TOTAL_HOURS = 60
    }
}