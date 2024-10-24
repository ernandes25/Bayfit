package com.baysoftware.bayfit.settings.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.baysoftware.bayfit.databinding.ActivitySettingsTimerSetterBinding
import com.baysoftware.bayfit.preferences.UserManager
import kotlinx.coroutines.launch

class SettingsTimerSetterActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsTimerSetterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsTimerSetterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNumberPicker(this) // Mova a chamada aqui
        binding.ok.setOnClickListener {
            saveTimerConfiguration(this)
            finish()
        }
    }

    private fun setupNumberPicker(context: Context) {
        lifecycleScope.launch {
            val user = UserManager.getInstance().readTimerConfiguration(context)
            binding.numberPickerMin.value = user.minute
            binding.numberPickerSec.value = user.second
        }
    }

    private fun saveTimerConfiguration(context: Context) {
        val vlrDoMinutoDoPicker = binding.numberPickerMin.value
        val vlrDoSegundoDoPicker = binding.numberPickerSec.value

        lifecycleScope.launch {
            UserManager.getInstance().saveTimerConfiguration(
                context,
                minute = vlrDoMinutoDoPicker,
                second = vlrDoSegundoDoPicker
            )
        }
    }
}