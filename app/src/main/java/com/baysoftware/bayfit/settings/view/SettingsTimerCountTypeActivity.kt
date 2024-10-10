package com.baysoftware.bayfit.settings.view

import android.os.Bundle
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
            } else if(restType == UserManager.TimerMode.PREDEFINED) {
                binding.radioButton2.isChecked = true
            }
        }

        lifecycleScope.launch {
            val button1 =
                UserManager.getInstance().readTimerMode(this@SettingsTimerCountTypeActivity)

            if (button1 == UserManager.TimerMode.FREE) {
                binding.radioButton1.isChecked = true
            } else {
                binding.radioButton2.isChecked = true
            }
        }

        binding.buttonOkcountType.setOnClickListener {

            lifecycleScope.launch {
                val selectedButton = if (binding.radioButton1.isChecked) {
                    // TODO: fix this navigation
//                    findNavController().navigate(R.id.fragment_home)
                    UserManager.TimerMode.FREE

                } else if (binding.radioButton2.isChecked) {
                    // TODO: fix this navigation
//                    val intent = Intent(this, TimerSetterActivity::class.java)
//                    startActivity(intent)
                    UserManager.TimerMode.PREDEFINED
                } else {
                    //TODO: Mostrar Toast "Selecione uma opção" e impedir navegação para a tela anterior
                    UserManager.TimerMode.UNDEFINED
                }
                UserManager.getInstance()
                    .saveTimerMode(this@SettingsTimerCountTypeActivity, selectedButton)
            }
        }
    }
}