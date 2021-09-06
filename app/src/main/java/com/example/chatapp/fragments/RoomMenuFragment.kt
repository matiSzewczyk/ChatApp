package com.example.chatapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chatapp.*
import com.example.chatapp.activities.ChatActivity
import com.example.chatapp.databinding.FragmentRoomMenuBinding
import com.example.chatapp.viewmodels.RoomMenuViewModel


class RoomMenuFragment : Fragment(R.layout.fragment_room_menu), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentRoomMenuBinding
    private val roomMenuViewModel: RoomMenuViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _partition = "partition"
        binding = FragmentRoomMenuBinding.bind(view)
        binding.roomPasswordInput.visibility = View.INVISIBLE

        val spinner = binding.spinner
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, roomMenuViewModel.getChatRooms())
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        binding.makeNewRoomButton.setOnClickListener {
            val action = RoomMenuFragmentDirections.actionRoomMenuFragmentToNewRoomFragment()
            findNavController().navigate(action)
        }

        binding.connectToRoomBtn.setOnClickListener {
            if (isPrivate()) {
                 showPasswordInput()
            } else {
                if (ConnectionChecker.isInternetAvailable(requireContext())) {
                    connect()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Unable to proceed. Please check your internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.roomPasswordInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val passwd = binding.roomPasswordInput.text.toString()
                binding.roomPasswordInput.text.clear()
                checkPassword(passwd)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun checkPassword(password: String) {
        if (password == roomMenuViewModel.roomList[roomMenuViewModel.index]!!.password) {
            binding.roomPasswordInput.visibility = View.INVISIBLE
            hideSoftKeyboard(binding.roomPasswordInput)
            connect()
        } else {
            Toast.makeText(requireContext(), "Incorrect password.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSoftKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        roomMenuViewModel.index = parent!!.selectedItemPosition
        if (!roomMenuViewModel.roomList[roomMenuViewModel.index]!!.isPrivate) {
            binding.roomPasswordInput.visibility = View.INVISIBLE
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun isPrivate(): Boolean {
        return roomMenuViewModel.roomList[roomMenuViewModel.index]!!.isPrivate
    }

    private fun showPasswordInput() {
        binding.roomPasswordInput.apply {
            visibility = View.VISIBLE
            requestFocus()
            showSoftKeyboard(this)
        }
    }

    private fun connect() {
        _partition = roomMenuViewModel.roomList[roomMenuViewModel.index]!!.name // Change the chat room
        val intent = Intent(this.requireContext(), ChatActivity::class.java)
        startActivity(intent)
    }
}