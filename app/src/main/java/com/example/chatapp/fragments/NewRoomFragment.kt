package com.example.chatapp.fragments
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chatapp.R
import com.example.chatapp.activities.ChatActivity
import com.example.chatapp.databinding.FragmentNewRoomBinding
import com.example.chatapp.viewmodels.NewRoomViewModel

class NewRoomFragment : Fragment(R.layout.fragment_new_room){

    private val newRoomViewModel: NewRoomViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNewRoomBinding.bind(view)

        binding.newRoomPassword.visibility = View.INVISIBLE

        binding.newRoomType.setOnCheckedChangeListener { _, id ->
            if (id == R.id.new_type_public) {
                binding.newRoomPassword.visibility = View.INVISIBLE
            }
            if (id == R.id.new_type_private) {
                binding.newRoomPassword.visibility = View.VISIBLE
            }
        }

        binding.createRoomButton.setOnClickListener {
            val roomName = binding.newRoomName.text.toString()
            val private = binding.newTypePrivate.isChecked
            var password = ""
            if (roomName.isNotEmpty()) {
                if (!roomName.contains(" ")) {
                    if (private) {
                        password = binding.newRoomPassword.text.toString()
                    }
                    if (newRoomViewModel.roomExists(roomName)) {
                        Toast.makeText(context, "Room already exists.", Toast.LENGTH_SHORT).show()
                    } else {
                        newRoomViewModel.createRoomObject(roomName, private, password)
                        goToChat()
                    }
                } else {
                    Toast.makeText(context, "Room name cannot contain whitespace.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Room name cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun goToChat() {
        val intent = Intent(this.requireContext(), ChatActivity::class.java)
        startActivity(intent)
    }
}
