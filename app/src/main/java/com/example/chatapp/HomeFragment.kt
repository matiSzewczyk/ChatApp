package com.example.chatapp

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)

        binding.connectBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragment2ToLoginFragment()
            findNavController().navigate(action)
        }

        binding.roomList.setOnCheckedChangeListener { _, i ->
            if (i == R.id.room_1) {
                println("hi there")
                _partition = "partition"
            }
            if (i == R.id.room_2) {
                _partition = "room2"
            }
        }
    }
}