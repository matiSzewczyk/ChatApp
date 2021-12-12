package com.example.chatapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatapp.ConnectionChecker
import com.example.chatapp.R
import com.example.chatapp.chatApp
import com.example.chatapp.databinding.FragmentRegisterBinding
import io.realm.mongodb.Credentials

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)

        // Log in button
        binding.loginButton.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        binding.confirmRegisterButton.setOnClickListener {
            if (inputsNotEmpty()) {
                if (!containsWhitespace()) {
                    if (passwordMatch()) {
                        if (passwordMinLength()) {
                            if (ConnectionChecker.isInternetAvailable(requireContext())) {
                                register()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Unable to proceed. Please check your internet connection",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun passwordMinLength(): Boolean {
        if (binding.registerPassword.length() >= 6) {
            return true
        }
        Toast.makeText(
            requireContext(),
            "Password must be at least 6 characters long.",
            Toast.LENGTH_SHORT
        ).show()
        binding.registerPassword.text.clear()
        binding.registerPasswordConfirm.text.clear()
        return false
    }

    private fun login() {
        val emailPasswordCredentials: Credentials = Credentials.emailPassword(
            binding.registerUsername.text.toString(),
            binding.registerPassword.text.toString()
        )
        chatApp.loginAsync(emailPasswordCredentials) {
            if (it.isSuccess) {
                Log.i("DEBUG", "Successfully logged in.")
            } else {
                Log.i("DEBUG", "Failed to log in.")
            }
        }
    }

    private fun register() {
        val email = binding.registerUsername.text.toString()
        val password = binding.registerPassword.text.toString()
        chatApp.emailPassword.registerUserAsync(email, password) {
            if (it.isSuccess) {
                Log.i("DEBUG", "Successfully registered user.")
                login()
                val action = RegisterFragmentDirections.actionRegisterFragmentToRoomMenuFragment()
                findNavController().navigate(action)
            } else {
                Log.i("DEBUG", it.error.toString())
            }
        }
    }

    private fun clearInput() = binding.apply {
        registerPasswordConfirm.text.clear()
        registerPassword.text.clear()
        registerUsername.text.clear()
    }

    private fun inputsNotEmpty(): Boolean = when {
        binding.registerUsername.text.isNotEmpty()
                && binding.registerPassword.text.isNotEmpty()
                && binding.registerPasswordConfirm.text.isNotEmpty() -> true
        else -> {
            Toast.makeText(
                requireContext(),
                "Cannot leave any fields empty.",
                Toast.LENGTH_SHORT
            ).show()
            false
        }

    }

    private fun containsWhitespace(): Boolean = when {
        binding.registerUsername.text.contains(" ") -> {
            Toast.makeText(
                requireContext(),
                "Username cannot contain whitespace.",
                Toast.LENGTH_SHORT
            ).show()
            true
        }
        else -> false
    }

    private fun passwordMatch(): Boolean = when {
        binding.registerPassword.text.toString() == binding.registerPasswordConfirm.text.toString() -> {
            true
        }
        else -> {
            binding.apply {
                registerPassword.text.clear()
                registerPasswordConfirm.text.clear()
            }
            Toast.makeText(requireContext(), "Passwords don't match", Toast.LENGTH_SHORT).show()
            false
        }
    }

    override fun onPause() {
        super.onPause()
        clearInput()
    }
}