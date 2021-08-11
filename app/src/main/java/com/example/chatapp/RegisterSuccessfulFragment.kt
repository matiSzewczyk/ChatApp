package com.example.chatapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class RegisterSuccessfulFragment : Fragment(R.layout.fragment_register_successful) {

    private val args: RegisterSuccessfulFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = view.findViewById<TextView>(R.id.registerSuccessfulUsername)
        username.text = args.username
    }
}
