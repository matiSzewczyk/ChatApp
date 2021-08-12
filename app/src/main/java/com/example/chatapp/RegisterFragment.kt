package com.example.chatapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

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

            if (passwd == passwdConfirm) {
                suspend {
                    val user = User(id + 1, email, uname, passwd)
                    chatApp.emailPassword.registerUser(email, passwd)
//                    realm.executeTransaction { transactionRealm ->
//                        transactionRealm.insert(user)
//                    }
                }
                val action = RegisterFragmentDirections
                    .actionRegisterFragmentToRegisterSuccessfulFragment(uname)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Passwords don't match.", Toast.LENGTH_SHORT).show()
                passwordInput.text.clear()
                passwordConfirmInput.text.clear()
            }
        }
    }
}
