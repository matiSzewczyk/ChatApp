package com.example.chatapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
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

    private val chatAdapter = ChatAdapter(mutableListOf(), this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        user = chatApp.currentUser()
        partition = "partition"
        val config = SyncConfiguration.Builder(user, partition).build()

        val recyclerView = findViewById<RecyclerView>(R.id.chat)
        recyclerView.adapter = chatAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        realm = Realm.getInstance(config)
        //find by id the button and input
//       val sendMessage = findViewById<Button>(R.id.sendMsgBtn)
        chatAdapter.messages = realm.where(Message::class.java).findAll()
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
//                val time = Calendar.getInstance().time.toString()
//                val message = findViewById<EditText>(R.id.chatInput).text.toString()
//                // Call the function and pass message
//                viewModel.sendMessage(message, time)
//            }
//        }
//    }

    override fun chatClickListener(position: Int, view: View?) {
        val msgTV = view?.findViewById<TextView>(R.id.messageText)
        val timeTV = view?.findViewById<TextView>(R.id.messageTime)
        println("clicked")

        msgTV?.text = chatInput?.text.toString()
        val time = Calendar.getInstance().time.toString()
        timeTV?.text = time
//        viewModel.sendMessage(msgTV.toString(), time)
    }
}