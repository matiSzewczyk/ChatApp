package com.example.chatapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.chatapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val profilePic = toolbar.findViewById<ImageView>(R.id.profile_picture)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)

        profilePic.setOnClickListener {
            val action: NavDirections = if (chatApp.currentUser()!!.profile.email != null) {
                NavGraphDirections.actionGlobalUserProfileFragment()
            } else {
                NavGraphDirections.actionGlobalLoginFragment()
            }
            navController.navigate(action)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
