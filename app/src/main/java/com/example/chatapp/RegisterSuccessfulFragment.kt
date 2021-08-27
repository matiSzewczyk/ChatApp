package com.example.chatapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.chatapp.databinding.FragmentRegisterSuccessfulBinding

class RegisterSuccessfulFragment : Fragment(R.layout.fragment_register_successful) {

    private lateinit var binding: FragmentRegisterSuccessfulBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterSuccessfulBinding.bind(view)
    }
}