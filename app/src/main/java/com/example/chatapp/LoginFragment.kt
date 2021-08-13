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
            val username = view.findViewById<EditText>(R.id.loginUsername).text.toString()

            chatApp.loginAsync(Credentials.anonymous()) {
                if (it.isSuccess) {
                    val action = LoginFragmentDirections.actionLoginFragmentToLoginSuccessfulFragment(username)
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(context, "kurzce", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}