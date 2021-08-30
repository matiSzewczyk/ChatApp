package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.chatapp.databinding.ActivityMainBinding
import io.realm.mongodb.User

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.profilePicture.setOnClickListener {
            val fragment: Fragment = if (chatApp.currentUser()!!.profile.email != null) {
                UserProfileFragment()
            } else {
                LoginFragment()
            }
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment, fragment)
                addToBackStack(null)
                commit()
            }
        }
    }
}
