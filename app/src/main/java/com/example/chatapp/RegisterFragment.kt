package com.example.chatapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register){

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)

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

    private fun register() {
        val email = binding.registerUsername.text.toString()
        val password = binding.registerPassword.text.toString()
        chatApp.emailPassword.registerUserAsync(email, password) {
            if (it.isSuccess) {
                Log.i("EXAMPLE", "Successfully registered user.")
            } else {
                Log.i("EXAMPLE", it.error.toString())
            }
        }
        val action = RegisterFragmentDirections.actionRegisterFragmentToRoomMenuFragment()
        findNavController().navigate(action)
    }

    private fun inputsNotEmpty(): Boolean {
        if (binding.registerUsername.text.isNotEmpty()
            && binding.registerPassword.text.isNotEmpty()
            && binding.registerPasswordConfirm.text.isNotEmpty()) {
            return true
        }
        Toast.makeText(requireContext(), "Cannot leave any fields empty.", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun containsWhitespace(): Boolean {
        if (binding.registerUsername.text.contains(" ")) {
            Toast.makeText(requireContext(), "Username cannot contain whitespace.", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun passwordMatch(): Boolean {
        if (binding.registerPassword.text.toString() == binding.registerPasswordConfirm.text.toString()) {
            return true
        }
        binding.apply {
            registerPassword.text.clear()
            registerPasswordConfirm.text.clear()
        }
        Toast.makeText(requireContext(), "Passwords don't match", Toast.LENGTH_SHORT).show()
        return false
    }
}