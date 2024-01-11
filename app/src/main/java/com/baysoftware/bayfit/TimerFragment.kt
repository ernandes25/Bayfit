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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.baysoftware.bayfit.databinding.FragmentTimerBinding
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match

class TimerFragment : Fragment () {

    private lateinit var binding: FragmentTimerBinding
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    val timerStarted = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_timer, container, false)

        binding.imageButtonParar.setOnClickListener { startStopTimer() }

        serviceIntent = Intent(requireContext(), TimerService::class.java)
        registerReceiver(requireContext(), updateTime, IntentFilter(TimerService.TIMER_UPDATE), RECEIVER_NOT_EXPORTED)
        return binding.root

    }


    private fun startStopTimer() {
        if (timerStarted)
            stopTimer()
    }

    private fun stopTimer() {
        requireActivity().stopService(serviceIntent)
        binding.imageButtonParar.setImageResource (R.drawable.ic_play)

    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            binding.timeTV.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 / 3600
        val seconds = resultInt % 86400 / 3600

        return makeTimeString(hours, minutes, seconds)

    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d", min, sec) //horas não será usada

}








