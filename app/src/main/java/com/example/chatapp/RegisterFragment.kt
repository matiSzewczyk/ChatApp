package com.example.chatapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import org.bson.types.ObjectId

class RegisterFragment : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailInput: EditText = view.findViewById(R.id.registerEmail)
        val usernameInput: EditText = view.findViewById(R.id.registerUsername)
        val passwordInput: EditText = view.findViewById(R.id.registerPasswd)
        val passwordConfirmInput: EditText = view.findViewById(R.id.registerPasswdConfirm)

        view.findViewById<Button>(R.id.confirmRegisterBtn).setOnClickListener {
            val email = emailInput.text.toString()
            val uname = usernameInput.text.toString()
            val passwd = passwordInput.text.toString()
            val passwdConfirm = passwordConfirmInput.text.toString()

            var user: User? = null
            if (passwd == passwdConfirm) {
                chatApp.emailPassword.registerUserAsync(email, passwd) {
                    if (it.isSuccess) {
                        user = chatApp.currentUser()
                        val action = RegisterFragmentDirections
                            .actionRegisterFragmentToRegisterSuccessfulFragment("placeholder")
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(context, "kurzce", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Passwords don't match.",
                    Toast.LENGTH_SHORT).show()
                passwordInput.text.clear()
                passwordConfirmInput.text.clear()
            }
        }
    }
}
