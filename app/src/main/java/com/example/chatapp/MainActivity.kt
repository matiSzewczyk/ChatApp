package com.example.chatapp

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.profilePicture.setOnClickListener {
            if (chatApp.currentUser()!!.profile.email != null) {
                // Go to profileSettingsFragment
            } else {
                // Go to loginFragment
            }
        }
    }
}
