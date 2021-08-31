package com.example.chatapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.chatapp.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
        val partition = _partition
        val user = chatApp.currentUser()
        val config = SyncConfiguration.Builder(user, partition).build()
        val realm = Realm.getInstance(config)

//        if (chatApp.currentUser()!!.profile.email != null) {
//            Toast.makeText(
//                applicationContext,
//                "sup",
//                Toast.LENGTH_SHORT
//            ).show()
//            val img = realm.where(ProfilePicture::class.java).equalTo("_id", user?.id).findFirst()
//            val newUri = img!!.picture.toString().toUri()
//            binding.profilePicture.setImageURI(newUri)
//        }

        binding.profilePicture.setOnClickListener {
            val action: NavDirections = if (chatApp.currentUser()!!.profile.email != null) {
                NavGraphDirections.actionGlobalUserProfileFragment()
            } else {
                NavGraphDirections.actionGlobalLoginFragment()
            }
            navController.navigate(action)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
