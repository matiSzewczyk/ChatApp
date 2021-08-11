package com.example.chatapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ChatApp)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                setReorderingAllowed(true)
                replace(R.id.nav_host_fragment, homeFragment)
                commit()
            }
        }
    }
}
