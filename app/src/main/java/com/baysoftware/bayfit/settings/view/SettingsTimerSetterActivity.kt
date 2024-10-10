package com.baysoftware.bayfit.settings.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
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
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        setupNumberPicker(context)

        binding.ok.setOnClickListener {
            saveTimerConfiguration(context)
            finish()
        }
        return super.onCreateView(name, context, attrs)
    }

    private fun setupNumberPicker(context: Context) {
        //TODO: consertar erro de não carrgar os valores do usuário após algumas tentativas

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