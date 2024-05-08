package com.baysoftware.bayfit.running.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.databinding.ActivityHistoryBinding
import com.baysoftware.bayfit.databinding.ActivityHomeBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding



        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
            setContentView(binding.root)

        }
    }
