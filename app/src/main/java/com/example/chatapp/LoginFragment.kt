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
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.mongodb.Credentials
import io.realm.mongodb.RealmResultTask

class LoginFragment : Fragment(R.layout.fragment_login), AdapterView.OnItemSelectedListener {

    private val chatRoomViewmodel: ChatRoomViewmodel by viewModels()
    private lateinit var roomList: RealmResults<ChatRoom>
    private lateinit var roomListener: RealmChangeListener<RealmResults<ChatRoom>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)
        roomList = chatRoomViewmodel.chatRoomList

        // Init the spinner
        val spinner = binding.spinner
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, roomList)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = this
        println("rooms: $roomList")

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
            if (binding.loginUsername.text.isNotEmpty()) {
                val action = LoginFragmentDirections.actionLoginFragmentToNewRoomFragment(binding.loginUsername.text.toString())
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        if (pos == 0) { // public
            _partition = roomList[0]!!.name
        }
        if (pos == 1) { // private
            _partition = roomList[1]!!.name
        }
        if (pos == 2) { // test
            _partition = roomList[2]!!.name
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}