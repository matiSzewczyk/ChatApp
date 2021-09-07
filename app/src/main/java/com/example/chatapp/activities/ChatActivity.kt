package com.example.chatapp.activities

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.ConnectionChecker
import com.example.chatapp.R
import com.example.chatapp._partition
import com.example.chatapp.adapters.ChatAdapter
import com.example.chatapp.chatApp
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.realm.Message
import com.example.chatapp.viewmodels.ChatViewModel
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
    //listener
    private lateinit var listener: RealmChangeListener<RealmResults<Message>>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        partition = _partition
        user = chatApp.currentUser()
        val config = SyncConfiguration.Builder(user, partition).build()
        realm = Realm.getInstance(config)

        chatAdapter = ChatAdapter(
            realm.where(Message::class.java)
                .findAll()
                .sort("timestamp", Sort.ASCENDING)
        )

        binding.apply {
            chat.apply {
                adapter = chatAdapter
                layoutManager = LinearLayoutManager(this@ChatActivity)
                if (chatAdapter.itemCount != 0) {
                    scrollToPosition(chatAdapter.itemCount - 1)
                }
            }
        }
        binding.sendMessageButton.setOnClickListener(this)

        listener = RealmChangeListener {
            chatAdapter.notifyDataSetChanged()
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
                            currentDateTime.toString()
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
}