package com.baysoftware.bayfit.running.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.baysoftware.bayfit.databinding.ActivityRunningResultBinding
import com.baysoftware.bayfit.util.getTimeStringFromDouble

const val END_TIME = "endTime"
const val END_REST = "endRest"

class RunningResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRunningResultBinding

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        binding.buttonReturn.setOnClickListener { finish() }
        binding.timeEnd.text = intent.getStringExtra(END_TIME)
        binding.restEnd.text = intent?.getDoubleExtra(END_TIME, 0.0)?.getTimeStringFromDouble()

        return super.onCreateView(name, context, attrs)
    }
}