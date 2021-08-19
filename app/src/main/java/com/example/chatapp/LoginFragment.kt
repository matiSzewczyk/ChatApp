package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chatapp.databinding.FragmentLoginBinding
import io.realm.RealmResults
import io.realm.mongodb.Credentials

class LoginFragment : Fragment(R.layout.fragment_login), AdapterView.OnItemSelectedListener {

    private val loginViewmodel: LoginViewmodel by viewModels()
    private lateinit var roomList: RealmResults<ChatRoom>
    private lateinit var binding: FragmentLoginBinding
    private var index = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _partition = "temp"
        binding = FragmentLoginBinding.bind(view)
        binding.roomPasswordInput.visibility = View.INVISIBLE
        roomList = loginViewmodel.chatRoomList
//        loginViewmodel.delete()
        for (i in 0 until roomList.size) {
            println("${roomList[i]!!.name}: ${roomList[i]!!.private}")
        }

        // Init the spinner
        val spinner = binding.spinner
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, roomList)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = this

        // Connect button logic
        binding.confirmConnectButton.setOnClickListener {
            if (binding.loginUsername.text.isNotEmpty()) {
                if (!binding.loginUsername.text.contains(" ")) {
                    if (isPrivate()) {
                        println("is private")
                        showPasswordInput()
                        if (!correctPassword()) {
                            Toast.makeText(requireContext(), "Incorrect password.", Toast.LENGTH_SHORT).show()
                        } else {
                            connect()
                        }
                    }
                    connect()
                } else {
                    Toast.makeText(requireContext(), "Username cannot contain whitespace.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.makeNewRoomButton.setOnClickListener {
            if (binding.loginUsername.text.isNotEmpty()) {
                val action = LoginFragmentDirections.actionLoginFragmentToNewRoomFragment(binding.loginUsername.text.toString())
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isPrivate(): Boolean {
        println("${roomList[index]!!.private}")
        return roomList[index]?.private!!
    }

    private fun showPasswordInput() {
        binding.roomPasswordInput.apply {
            visibility = View.VISIBLE
            requestFocus()
        }
    }

    private fun correctPassword() : Boolean {
        var correct = false
        binding.roomPasswordInput.setOnEditorActionListener { textView, i, keyEvent ->
            val passwd = binding.roomPasswordInput.text.toString()
            if (passwd == roomList[index]!!.password) {
                correct = true
            }
            true
        }
        return correct
    }

    private fun connect() {
        chatApp.loginAsync(Credentials.anonymous()) {
            if (it.isSuccess) {
                _partition = roomList[index]!!.name // Change the chat room
                val username = binding.loginUsername.text.toString()
                val intent = Intent(this.requireContext(), ChatActivity::class.java)
                intent.putExtra("username", username)//send the username from input
                startActivity(intent)
            } else {
                Toast.makeText(context, "An error occurred.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        index = parent!!.selectedItemPosition
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}