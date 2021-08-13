package com.example.chatapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import io.realm.Realm
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration

class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.confirmLoginBtn).setOnClickListener {
            val emailInput: EditText = view.findViewById(R.id.loginEmail)
            val email = emailInput.text.toString()
            val passwordInput: EditText = view.findViewById(R.id.loginPassword)
            val password = passwordInput.text.toString()

            val _user = MutableLiveData<User?>()
            var user: User? = null
            val creds: Credentials = Credentials.emailPassword(email, password)
                chatApp.loginAsync(creds) {
                    if (it.isSuccess) {
                        user = chatApp.currentUser()
                        val action = LoginFragmentDirections.actionLoginFragmentToLoginSuccessfulFragment("placeholder")
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(context, "kurzce", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}