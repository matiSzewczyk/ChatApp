package com.example.chatapp
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.chatapp.databinding.FragmentNewRoomBinding

class NewRoomFragment : Fragment(R.layout.fragment_new_room){

    private val viewModel: NewRoomViewmodel by viewModels()
    private val args: NewRoomFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNewRoomBinding.bind(view)

        binding.newRoomPassword.visibility = View.INVISIBLE

        binding.newRoomType.setOnCheckedChangeListener { _, i ->
            if (i == R.id.new_type_public) {
                binding.newRoomPassword.visibility = View.INVISIBLE
                println("is checked: ${binding.newTypePrivate.isChecked}")
            }
            if (i == R.id.new_type_private) {
                binding.newRoomPassword.visibility = View.VISIBLE
                println("is checked: ${binding.newTypePrivate.isChecked}")
            }
        }

        binding.createRoomButton.setOnClickListener {
            val roomName = binding.newRoomName.text.toString()
            val private: Boolean = binding.newTypePrivate.isChecked
            var password = ""
            val newRoom = ChatRoom()
            if (roomName.isNotEmpty()) {
                if (!roomName.contains(" ")) {
                    if (private) {
                        password = binding.newRoomPassword.text.toString()
                    }
                    println("setting the private var to: $private")
                    newRoom.name = roomName
                    newRoom.private = private
                    newRoom.password = password
                    if (viewModel.exists(roomName)) {
                        Toast.makeText(context, "Room already exists.", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.makeNewRoom(newRoom)
                        goToChat()
                        _partition = newRoom.name
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
        val username = args.username
        println("current partition: $_partition")
        val intent = Intent(this.requireContext(), ChatActivity::class.java)
        intent.putExtra("username", username)//send the username from input
        startActivity(intent)
    }
}
