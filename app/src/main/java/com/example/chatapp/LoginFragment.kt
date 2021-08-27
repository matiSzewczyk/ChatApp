package com.example.chatapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatapp.databinding.FragmentLoginBinding
import io.realm.mongodb.Credentials.emailPassword

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _partition = "partition"
        binding = FragmentLoginBinding.bind(view)

        // Connect button logic
        binding.confirmConnectButton.setOnClickListener {
            if (inputsNotEmpty()) {
                if (!containsWhitespace()) {
                    login()
                }
            }
        }
    }

    private fun login() {
        val emailPasswordCredentials: io.realm.mongodb.Credentials = emailPassword(
            binding.loginUsername.text.toString(),
            binding.loginPassword.text.toString()
        )
        chatApp.loginAsync(emailPasswordCredentials) {
            if (it.isSuccess) {
                Log.i("AUTH", "success")
                val action = LoginFragmentDirections.actionLoginFragmentToRoomMenuFragment()
                findNavController().navigate(action)
            } else {
                Log.i("AUTH", it.error.toString())
            }
//            val user = chatApp.currentUser()
//            val username = user?.profile?.email
//            Toast.makeText(requireContext(), username, Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputsNotEmpty(): Boolean {
        if (binding.loginUsername.text.isNotEmpty()
            && binding.loginPassword.text.isNotEmpty()) {
            return true
        }
        Toast.makeText(requireContext(), "Cannot leave any fields empty.", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun containsWhitespace(): Boolean {
        if (binding.loginUsername.text.contains(" ")) {
            Toast.makeText(requireContext(), "Username cannot contain whitespace.", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }
}