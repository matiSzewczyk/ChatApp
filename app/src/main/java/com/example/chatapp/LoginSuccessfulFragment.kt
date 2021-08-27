package com.example.chatapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.chatapp.databinding.FragmentLoginSuccessfulBinding

class LoginSuccessfulFragment : Fragment(R.layout.fragment_login_successful){

    private val args: LoginSuccessfulFragmentArgs by navArgs()
    private lateinit var binding: FragmentLoginSuccessfulBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginSuccessfulBinding.bind(view)

        binding.username.text = args.username
    }
}