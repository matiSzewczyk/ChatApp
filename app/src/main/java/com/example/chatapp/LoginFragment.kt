package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chatapp.databinding.FragmentLoginBinding
import io.realm.mongodb.Credentials

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)

        binding.confirmLoginBtn.setOnClickListener {
            chatApp.loginAsync(Credentials.anonymous()) {
                if (it.isSuccess) {
                    startActivity(Intent(this.requireContext(), ChatActivity::class.java))
                } else {
                    Toast.makeText(context, "An error occurred.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}