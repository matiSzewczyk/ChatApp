package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.chatapp.databinding.FragmentLoginSuccessfulBinding

class LoginSuccessfulFragment : Fragment(R.layout.fragment_login_successful) {

    private val args: LoginSuccessfulFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginSuccessfulBinding.bind(view)

        val username = binding.loginSuccessfulUsername
        username.text = args.username
        binding.loginOk.setOnClickListener {
            startActivity(Intent(this.requireContext(), ChatActivity::class.java))
        }
    }
}