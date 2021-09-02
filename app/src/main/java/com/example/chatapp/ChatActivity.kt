package com.example.chatapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.ActivityChatBinding
import io.realm.*
import io.realm.mongodb.sync.SyncConfiguration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityChatBinding
    private var user: io.realm.mongodb.User? = null
    private lateinit var partition: String
    private lateinit var realm: Realm
    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter
    private var profilePic: ByteArray? = null
    //listener
    private lateinit var listener: RealmChangeListener<RealmResults<Message>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        partition = _partition
        user = chatApp.currentUser()
        var config = SyncConfiguration.Builder(user, partition).build()
        realm = Realm.getInstance(config)

        if (isNewUser()) {
            partition = "partition"
            config = SyncConfiguration.Builder(user, partition).build()
            realm = Realm.getInstance(config)
            profilePic = realm.where(ProfilePicture::class.java).equalTo("id", user?.id).findFirst()?.picture
            partition = _partition
            config = SyncConfiguration.Builder(user, partition).build()
            realm = Realm.getInstance(config)
            chatViewModel.addNewUser(realm, profilePic, user!!.id)
        }

        chatAdapter = ChatAdapter(
            realm.where(ChatRoomUsers::class.java)
                .findAll(),
            realm.where(Message::class.java)
                .findAll()
                .sort("timestamp", Sort.ASCENDING)
        )

        binding.apply {
            chat.apply {
                adapter = chatAdapter
                layoutManager = LinearLayoutManager(this@ChatActivity)
                scrollToPosition(chatAdapter.itemCount - 1)
            }
        }
        binding.sendMessageButton.setOnClickListener(this)

        listener = RealmChangeListener {
            chatAdapter.notifyItemChanged(chatAdapter.itemCount - 1)
            binding.chat.scrollToPosition(chatAdapter.itemCount - 1)
        }
        chatAdapter.messages.addChangeListener(listener)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.send_message_button -> {
                val currentDateTime = LocalDateTime.now()
                val message = binding.chatInput.text.toString()

                if (ConnectionChecker.isInternetAvailable(applicationContext)) {
                    if (message.isNotEmpty()) {
                        val obj = chatViewModel.createObject(
                            chatApp.currentUser()!!.profile.email.toString(),
                            binding.chatInput.text.toString(),
                            currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)).toString(),
                            currentDateTime.toString(),
                            user!!.id
                        )
                        chatViewModel.sendMessage(realm, obj)
                    }
                    // DEBUG ONLY // isPrivate this to clear db
                    if (message == "cleardb") {
                        chatViewModel.clearDatabase(realm)
                    }
                    // DEBUG ONLY
                }
                binding.chat.scrollToPosition(chatAdapter.itemCount - 1)
                binding.chatInput.text.clear()
                binding.chatInput.clearFocus()
                hideSoftKeyboard(binding.chatInput)
            }
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

    private fun isNewUser(): Boolean {
        if (realm.where(ChatRoomUsers::class.java).equalTo("id", user!!.id).findFirst() != null)
            return false
        return true
    }
}