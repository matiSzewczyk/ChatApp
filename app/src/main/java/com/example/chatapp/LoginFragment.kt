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
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import java.lang.Exception

class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val realm = Realm.getInstance(chatApp)

        view.findViewById<Button>(R.id.confirmLoginBtn).setOnClickListener {
            val usernameInput: EditText = view.findViewById(R.id.loginUsername)
            val username = usernameInput.text.toString()
            val passwordInput: EditText = view.findViewById(R.id.loginPassword)
            val passwd = passwordInput.text.toString()

            try {
                realm.executeTransaction { transactionRealm ->
                    val uname: String = transactionRealm.where<User>()
                        .equalTo("username", username)
                        .and()
                        .equalTo("password", passwd)
                        .toString()
                    val action = LoginFragmentDirections.actionLoginFragmentToLoginSuccessfulFragment(uname)
                    findNavController().navigate(action)
                }
            } catch (e: Exception) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                Log.e(tag, "Error:  ${e.message}")
            }
        }
    }
}