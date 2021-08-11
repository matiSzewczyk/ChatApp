package com.example.chatapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class RegisterFragment : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.confirmRegisterBtn).setOnClickListener {
            val usernameInput: EditText = view.findViewById(R.id.registerUsername)
            val username = usernameInput.text.toString()
            val action = RegisterFragmentDirections.actionRegisterFragmentToRegisterSuccessfulFragment(username)
            findNavController().navigate(action)
        }
    }
}
