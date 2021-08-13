package com.example.chatapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import java.util.*

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    private var user: io.realm.mongodb.User? = null

    private val chatAdapter = ChatAdapter(mutableListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        user = chatApp.currentUser()

        val recyclerView = findViewById<RecyclerView>(R.id.chat)
        recyclerView.adapter = chatAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //find by id the button and input
        val sendMessage = findViewById<Button>(R.id.sendMsgBtn)
        sendMessage.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.sendMsgBtn -> {
                // I guess here is where the viewmodel comes into play
                // I need to use one of the methods in there that will handle the data i pass and send it to the local/remote db
                val messageText = findViewById<EditText>(R.id.chatInput)
                val time = Calendar.getInstance().time.toString()
                val message = messageText.text.toString()
                // Call the function and pass message
            }
        }
    }
}