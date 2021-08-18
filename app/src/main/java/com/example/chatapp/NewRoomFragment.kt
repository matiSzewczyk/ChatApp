package com.example.chatapp
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.chatapp.databinding.FragmentNewRoomBinding

class NewRoomFragment : Fragment(R.layout.fragment_new_room){

    private val viewModel: ChatRoomViewmodel by viewModels()
    val args: NewRoomFragmentArgs by navArgs()

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
            val password = binding.newRoomPassword.text.toString()
            val newRoom = ChatRoom()
            newRoom.name = roomName
            newRoom.private = private
            if (private) {
                newRoom.password = password
            }
            viewModel.makeNewRoom(newRoom)
            _partition = newRoom.name
            val username = args.username
            val intent = Intent(this.requireContext(), ChatActivity::class.java)
            intent.putExtra("username", username)//send the username from input
            startActivity(intent)
        }
    }
}
