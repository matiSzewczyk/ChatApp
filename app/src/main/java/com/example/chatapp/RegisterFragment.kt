package com.example.chatapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class RegisterFragment : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usernameInput: EditText = view.findViewById(R.id.registerUsername)
        val passwordInput: EditText = view.findViewById(R.id.registerPasswd)
        val passwordConfirmInput: EditText = view.findViewById(R.id.registerPasswdConfirm)

        view.findViewById<Button>(R.id.confirmRegisterBtn).setOnClickListener {
            val username = usernameInput.text.toString()
            if (passwordInput == passwordConfirmInput) {
                val action = RegisterFragmentDirections.actionRegisterFragmentToRegisterSuccessfulFragment(username)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Passwords don't match.", Toast.LENGTH_SHORT).show()
                passwordInput.text.clear()
                passwordConfirmInput.text.clear()
            }
        }
    }
}
