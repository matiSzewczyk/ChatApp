package com.example.chatapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.ActivityChatBinding
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration
import java.util.*

class ChatActivity : AppCompatActivity(), ChatInterface, View.OnClickListener {

    private lateinit var chatInput: EditText
    private var user: io.realm.mongodb.User? = null
    private lateinit var partition: String
    private lateinit var realm: Realm
    private val viewModel: ChatViewmodel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        partition = "partition"
        user = chatApp.currentUser()
        val config = SyncConfiguration.Builder(user, partition).build()
        realm = Realm.getInstance(config)

        chatAdapter = ChatAdapter(mutableListOf(), this)
        chatAdapter.messages = realm.where(Message::class.java).findAll()

        binding.apply {
            chat.apply {
                adapter = chatAdapter
                layoutManager = LinearLayoutManager(this@ChatActivity)
            }
        }

        binding.sendMsgBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.sendMsgBtn -> {
                println("hello")
                val time = Calendar.getInstance().time.toString()
                val message = findViewById<EditText>(R.id.chatInput).text.toString()

                realm.executeTransactionAsync { bgRealm ->
                    val xd = Message()
                    xd.message = message
                    xd.time = time
                    bgRealm.copyToRealmOrUpdate(xd)
                }
                // Call the function and pass message
                val test = findViewById<TextView>(R.id.messageText)
//                chatAdapter.messages.add(xd)
//                chatAdapter.notifyItemInserted(chatAdapter.itemCount - 1)
//                viewModel.sendMessage(message, time, test)
            }
        }
    }

    override fun chatClickListener(position: Int, view: View) {
        val msgTV = view.findViewById<TextView>(R.id.messageText)
        val timeTV = view.findViewById<TextView>(R.id.messageTime)

        println("i think it works")
        Toast.makeText(this, "click works", Toast.LENGTH_SHORT).show()

        msgTV.text = chatInput.text.toString()
        val time = Calendar.getInstance().time.toString()
        timeTV.text = time
//        viewModel.sendMessage(msgTV.toString(), time)
    }
}