package com.example.chatapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginBtn: Button = view.findViewById(R.id.loginBtn)
        val registerBtn: Button = view.findViewById(R.id.registerBtn)

        loginBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragment2ToLoginFragment()
            findNavController().navigate(action)
        }

        registerBtn.isEnabled = false
        registerBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragment2ToRegisterFragment()
            findNavController().navigate(action)
        }
    }
}