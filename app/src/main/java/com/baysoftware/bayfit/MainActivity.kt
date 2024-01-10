package com.baysoftware.bayfit

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.baysoftware.bayfit.databinding.ActivityMainBinding
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //  setContentView(R.layout.activity_main) linha que estava antes de colocar a linha abaixo
        setContentView(binding.root)// verificar easa linha é assim que está no video
     //   val navHostFragment =
      //      supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
     //   val navController: NavController = navHostFragment.navController
     //   NavigationUI.setupActionBarWithNavController(this, navController)
    }
}





