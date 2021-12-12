package com.example.chatapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatapp.*
import com.example.chatapp.databinding.FragmentLoginBinding
import io.realm.Realm
import io.realm.mongodb.Credentials.emailPassword
import io.realm.mongodb.sync.SyncConfiguration

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        // Sign up button
        binding.signUpButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        // Connect button logic
        binding.confirmConnectButton.setOnClickListener {
            if (inputsNotEmpty()) {
                if (!containsWhitespace()) {
                    if (ConnectionChecker.isInternetAvailable(requireContext())) {
                        login()
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

    private fun login() {
        val emailPasswordCredentials: io.realm.mongodb.Credentials = emailPassword(
            binding.loginUsername.text.toString(),
            binding.loginPassword.text.toString()
        )
        chatApp.loginAsync(emailPasswordCredentials) {
            if (it.isSuccess) {
                val action = LoginFragmentDirections.actionLoginFragmentToRoomMenuFragment()
                createRealm()
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Name or password incorrect.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createRealm() {
        user = chatApp.currentUser()!!
        config = SyncConfiguration.Builder(user, _partition).build()
        realm = Realm.getInstance(config)
    }

    private fun clearInput() {
        binding.loginPassword.text.clear()
        binding.loginUsername.text.clear()
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
        return if (binding.loginUsername.text.contains(" ")) {
            Toast.makeText(requireContext(), "Username cannot contain whitespace.", Toast.LENGTH_SHORT).show()
            true
        } else false
    }

    override fun onPause() {
        super.onPause()
        clearInput()
    }
}