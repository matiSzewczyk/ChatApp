package com.example.chatapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.realm.mongodb.Credentials

class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.confirmLoginBtn).setOnClickListener {
            val emailInput: EditText = view.findViewById(R.id.loginEmail)
            val email = emailInput.text.toString()
            val passwordInput: EditText = view.findViewById(R.id.loginPassword)
            val passwd = passwordInput.text.toString()

            val creds = Credentials.emailPassword(email, passwd)
            try {
                chatApp.login(creds)
                val action = LoginFragmentDirections.actionLoginFragmentToLoginSuccessfulFragment("placeholder")
                findNavController().navigate(action)
            } catch (e: Exception) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                Log.e(tag, "Error:  ${e.message}")
            }
        }
    }
}