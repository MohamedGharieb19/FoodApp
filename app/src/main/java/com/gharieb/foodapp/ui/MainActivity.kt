package com.gharieb.foodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gharieb.foodapp.R
import com.gharieb.foodapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavigation()

    }

    private fun setUpNavigation() {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.host_fragment)
        return navController.navigateUp()
    }
}