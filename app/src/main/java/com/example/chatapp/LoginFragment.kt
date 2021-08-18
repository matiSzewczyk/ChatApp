package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chatapp.databinding.FragmentLoginBinding
import io.realm.mongodb.Credentials

class LoginFragment : Fragment(R.layout.fragment_login), AdapterView.OnItemSelectedListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)

        // Init the spinner
        val spinner = binding.spinner

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.room_array,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this

        // Connect button logic
        binding.confirmConnectButton.setOnClickListener {
            if (binding.loginUsername.text.isNotEmpty()) {
                if (!binding.loginUsername.text.contains(" ")) {
                    chatApp.loginAsync(Credentials.anonymous()) {
                        if (it.isSuccess) {
                            val username = binding.loginUsername.text.toString()
                            val intent = Intent(this.requireContext(), ChatActivity::class.java)
                            intent.putExtra("username", username)//send the username from input
                            startActivity(intent)
                        } else {
                            Toast.makeText(context, "An error occurred.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Username cannot contain whitespace.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.makeNewRoomButton.setOnClickListener {
            Toast.makeText(requireContext(), "hi.", Toast.LENGTH_SHORT).show()
            if (binding.loginUsername.text.isNotEmpty()) {
// TODO: 8/18/21 add a createRoomFragment 
            } else {
                Toast.makeText(requireContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        if (pos == 0) { // public
            _partition = "room0"
        }
        if (pos == 1) { // private
            _partition = "room1"
        }
        if (pos == 2) { // test
            _partition = "room2"
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}