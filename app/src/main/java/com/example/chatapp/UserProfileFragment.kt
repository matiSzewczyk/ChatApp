package com.example.chatapp

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.chatapp.databinding.FragmentUserProfileBinding

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private lateinit var binding: FragmentUserProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserProfileBinding.bind(view)

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            binding.userProfilePicture.setImageURI(it)
        }

        binding.userProfilePictureButton.setOnClickListener {
            getImage.launch("image/*")
        }
    }
}