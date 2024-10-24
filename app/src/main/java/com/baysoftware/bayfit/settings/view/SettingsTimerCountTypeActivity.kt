package com.baysoftware.bayfit.settings.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.baysoftware.bayfit.databinding.ActivitySettingsTimerCountTypeBinding
import com.baysoftware.bayfit.preferences.UserManager
import kotlinx.coroutines.launch

class SettingsTimerCountTypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsTimerCountTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsTimerCountTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        lifecycleScope.launch {
            val restType =
                UserManager.getInstance().readTimerMode(this@SettingsTimerCountTypeActivity)

            if (restType == UserManager.TimerMode.FREE) {
                binding.radioButton1.isChecked = true
            } else if (restType == UserManager.TimerMode.PREDEFINED) {
                binding.radioButton2.isChecked = true
            }
        }

        binding.buttonOkcountType.setOnClickListener {
            lifecycleScope.launch {

                val selectedButton = if (binding.radioButton1.isChecked) {
                    finish()
                    UserManager.TimerMode.FREE
                } else if (binding.radioButton2.isChecked) {
                    val intent = Intent(
                        this@SettingsTimerCountTypeActivity,
                        SettingsTimerSetterActivity::class.java
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish() // Finaliza a atividade atual
                    UserManager.TimerMode.PREDEFINED
                } else {
                    Toast.makeText(
                        this@SettingsTimerCountTypeActivity,
                        "Por favor, selecione uma opção",
                        Toast.LENGTH_SHORT
                    ).show()
                    UserManager.TimerMode.UNDEFINED
                }
                UserManager.getInstance()
                    .saveTimerMode(this@SettingsTimerCountTypeActivity, selectedButton)
            }
        }
    }
}