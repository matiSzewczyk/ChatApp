package com.example.chatapp
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.chatapp.databinding.FragmentNewRoomBinding

class NewRoomFragment : Fragment(R.layout.fragment_new_room){
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

        }
    }
}
