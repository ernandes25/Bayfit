package com.baysoftware.bayfit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.baysoftware.bayfit.databinding.ActivityHomeBinding
import com.baysoftware.bayfit.history.view.HistoryListActivity
import com.baysoftware.bayfit.running.view.RunningTimerActivity
import com.baysoftware.bayfit.settings.view.SettingsActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonConfig.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.imageButtonIniciar.setOnClickListener {
            val intent = Intent(this, RunningTimerActivity::class.java)
            startActivity(intent)
        }

        binding.buttonHistory.setOnClickListener {
            val intent = Intent(this, HistoryListActivity::class.java)
            startActivity(intent)
        }
    }
}