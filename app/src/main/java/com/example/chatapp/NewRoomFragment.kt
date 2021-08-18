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

    private val viewModel: ChatRoomViewmodel by viewModels()
    private val args: NewRoomFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNewRoomBinding.bind(view)

        binding.newRoomPassword.visibility = View.INVISIBLE

        binding.newRoomType.setOnCheckedChangeListener { _, i ->
            if (i == R.id.new_type_public) {
                binding.newRoomPassword.visibility = View.INVISIBLE
            }
            if (i == R.id.new_type_private) {
                binding.newRoomPassword.visibility = View.VISIBLE
            }

        }

        binding.createRoomButton.setOnClickListener {
            val roomName = binding.newRoomName.text.toString()
            val private = binding.newTypePrivate.isChecked
            var password = ""
            val newRoom = ChatRoom()
            if (roomName.isNotEmpty()) {
                if (!roomName.contains(" ")) {
                    if (private) {
                        password = binding.newRoomPassword.text.toString()
                    }
                    newRoom.name = roomName
                    newRoom.private = private
                    newRoom.password = password
                    viewModel.makeNewRoom(newRoom)
                    _partition = newRoom.name
                    goToChat()
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
        val intent = Intent(this.requireContext(), ChatActivity::class.java)
        intent.putExtra("username", username)//send the username from input
        startActivity(intent)
    }
}
