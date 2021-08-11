package com.example.chatapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class LoginSuccessfulFragment : Fragment(R.layout.fragment_login_successful) {

    private val args: LoginSuccessfulFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = view.findViewById<TextView>(R.id.loginSuccessfulUsername)
        username.text = args.username
    }
}