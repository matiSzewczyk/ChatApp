package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.realm.Realm
import io.realm.RealmConfiguration

class RegisterFragment : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = RealmConfiguration.Builder().name("appdatabase.db").allowWritesOnUiThread(true).build()
        val realm = Realm.getInstance(config)

        val usernameInput: EditText = view.findViewById(R.id.registerUsername)
        val passwordInput: EditText = view.findViewById(R.id.registerPasswd)
        val passwordConfirmInput: EditText = view.findViewById(R.id.registerPasswdConfirm)

        view.findViewById<Button>(R.id.confirmRegisterBtn).setOnClickListener {
            val passwd = passwordInput.text.toString()
            val passwdConfirm = passwordConfirmInput.text.toString()
            val username = usernameInput.text.toString()

            if (passwd == passwdConfirm) {
                val action = RegisterFragmentDirections.actionRegisterFragmentToRegisterSuccessfulFragment(username)
                findNavController().navigate(action)
                val user = User(username, passwd)
                realm.executeTransaction { transactionRealm ->
                    transactionRealm.insert(user)
                }
            } else {
                Toast.makeText(requireContext(), "Passwords don't match.", Toast.LENGTH_SHORT).show()
                passwordInput.text.clear()
                passwordConfirmInput.text.clear()
            }
        }
    }
}
