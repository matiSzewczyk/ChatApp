package com.example.chatapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.confirmLoginBtn).setOnClickListener {
            val usernameInput: EditText = view.findViewById(R.id.loginUsername)
            val username = usernameInput.text.toString()
            val action = LoginFragmentDirections.actionLoginFragmentToLoginSuccessfulFragment(username)
            findNavController().navigate(action)
        }
    }
}