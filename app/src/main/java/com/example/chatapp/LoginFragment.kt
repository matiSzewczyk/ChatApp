package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chatapp.databinding.FragmentLoginBinding
import io.realm.mongodb.Credentials

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _partition = "temp"
        binding = FragmentLoginBinding.bind(view)

        // Connect button logic
        binding.confirmConnectButton.setOnClickListener {
            if (binding.loginUsername.text.isNotEmpty()) {
                if (!binding.loginUsername.text.contains(" ")) {
                    if (ConnectionChecker.isInternetAvailable(requireContext())) {
                        connect()
                    } else {
                        Toast.makeText(requireContext(), "Error: No connection found.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Username cannot contain whitespace.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }


    }


    private fun connect() {
        chatApp.loginAsync(Credentials.anonymous()) {
            if (it.isSuccess) {
                val username = binding.loginUsername.text.toString()
                val intent = Intent(this.requireContext(), ChatActivity::class.java)
                intent.putExtra("username", username)//send the username from input
                startActivity(intent)
            } else {
                Toast.makeText(context, "An error occurred.", Toast.LENGTH_SHORT).show()
            }
        }
    }




}