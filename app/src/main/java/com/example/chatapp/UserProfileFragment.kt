package com.example.chatapp

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.chatapp.databinding.FragmentUserProfileBinding
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private lateinit var binding: FragmentUserProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserProfileBinding.bind(view)

        val partition = _partition
        val user = chatApp.currentUser()
        val config = SyncConfiguration.Builder(user, partition).build()
        val realm = Realm.getInstance(config)
//        val xd = realm.where(ProfilePicture::class.java).equalTo("id", user?.id).findFirst()
//        val newUri = xd!!.picture.toString().toUri()
//        binding.userProfilePicture.setImageURI(newUri)

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            val stream = activity?.contentResolver?.openInputStream(it)
            val img = stream!!.readBytes()
            val picture = ProfilePicture()
            picture.picture = img
            realm.executeTransactionAsync { bgRealm ->
                bgRealm.copyToRealmOrUpdate(picture)
            }
            binding.userProfilePicture.setImageURI(it)
        }

        binding.userProfilePictureButton.setOnClickListener {
            getImage.launch("image/*")
        }
    }
}