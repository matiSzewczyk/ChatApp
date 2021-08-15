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
    private val viewModel: ChatViewmodel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        partition = "partition"
        user = chatApp.currentUser()
        val config = SyncConfiguration.Builder(user, partition)
            .allowWritesOnUiThread(true)
            .build()
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
                scrollToPosition(chatAdapter.itemCount - 1)
            }
        }
        binding.sendMessageButton.setOnClickListener(this)
        // Run this is i need to delete the table contents
//        realm.executeTransaction {
//
//            realm.delete(Message::class.java)
//        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.send_message_button -> {
                val currentDateTime = LocalDateTime.now()
                val username = intent.getStringExtra("username").toString()
                val timestamp = currentDateTime.toString()
                val time = currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)).toString()
                val message = binding.chatInput.text.toString()

                if (message.isNotEmpty()) {
                    realm.executeTransactionAsync { bgRealm ->
                        val xd = Message()
                        xd.username = username
                        xd.message = message
                        xd.time = time
                        xd.timestamp = timestamp
                        bgRealm.copyToRealmOrUpdate(xd)
                    }
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