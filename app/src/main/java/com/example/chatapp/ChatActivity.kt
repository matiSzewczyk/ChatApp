package com.example.chatapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration
import java.util.*

class ChatActivity : AppCompatActivity(), ChatInterface {

    private lateinit var chatInput: EditText
    private var user: io.realm.mongodb.User? = null
    private lateinit var partition: String
    private lateinit var realm: Realm
    private val viewModel: ChatViewmodel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        partition = "partition"
        user = chatApp.currentUser()
        val config = SyncConfiguration.Builder(user, partition).build()
        realm = Realm.getInstance(config)

        chatAdapter = ChatAdapter(mutableListOf(), this)
        chatAdapter.messages = realm.where(Message::class.java).findAll()

        val recyclerView = findViewById<RecyclerView>(R.id.chat)
        recyclerView.adapter = chatAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //find by id the button and input
//       val sendMessage = findViewById<Button>(R.id.sendMsgBtn)
        chatInput = findViewById<EditText>(R.id.chatInput)

    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

//    override fun onClick(view: View?) {
//        when (view?.id) {
//            R.id.sendMsgBtn -> {
//                println("hello")
//                val time = Calendar.getInstance().time.toString()
//                val message = findViewById<EditText>(R.id.chatInput).text.toString()
//                // Call the function and pass message
//                val test = findViewById<TextView>(R.id.messageText)
//                viewModel.sendMessage(message, time, test)
//            }
//        }
//    }

    override fun chatClickListener(position: Int, view: View) {
        val msgTV = view.findViewById<TextView>(R.id.messageText)
        val timeTV = view.findViewById<TextView>(R.id.messageTime)
        Toast.makeText(this, "click works", Toast.LENGTH_SHORT).show()

        msgTV.text = chatInput.text.toString()
        val time = Calendar.getInstance().time.toString()
        timeTV.text = time
//        viewModel.sendMessage(msgTV.toString(), time)
    }
}