package com.example.chatapp.activities

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.ConnectionChecker
import com.example.chatapp.R
import com.example.chatapp.adapters.ChatAdapter
import com.example.chatapp.chatApp
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.realm.Message
import com.example.chatapp.viewmodels.ChatViewModel
import io.realm.RealmChangeListener
import io.realm.RealmResults
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityChatBinding
    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var listener: RealmChangeListener<RealmResults<Message>>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        binding.sendMessageButton.setOnClickListener(this)

        listener = RealmChangeListener {
            chatAdapter.notifyDataSetChanged()
            binding.chat.scrollToPosition(chatAdapter.itemCount - 1)
        }
        chatAdapter.messages.addChangeListener(listener)

    }

    private fun setupRecyclerView() = binding.chat.apply {
        chatAdapter = ChatAdapter(
            chatViewModel.getMessages()
        )
        adapter = chatAdapter
        layoutManager = LinearLayoutManager(this@ChatActivity)
        if (chatAdapter.itemCount != 0) {
            scrollToPosition(chatAdapter.itemCount - 1)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.send_message_button -> {
                val currentDateTime = LocalDateTime.now()
                val message = binding.chatInput.text.toString()

                if (ConnectionChecker.isInternetAvailable(applicationContext)) {
                    if (message.isNotEmpty()) {
                        chatViewModel.createObject(
                            chatApp.currentUser()!!.profile.email.toString(),
                            binding.chatInput.text.toString(),
                            currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM))
                                .toString(),
                            currentDateTime.toString()
                        )
                        // DEBUG ONLY
                        if (message == "cleardb") {
                            chatViewModel.clearDatabase()
                        }
                        // DEBUG ONLY
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "No connection.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                resetUi()
            }
        }
    }

    private fun resetUi() {
        binding.chat.scrollToPosition(chatAdapter.itemCount - 1)
        binding.chatInput.text.clear()
        binding.chatInput.clearFocus()
        hideSoftKeyboard(binding.chatInput)
    }

    private fun hideSoftKeyboard(view: View) {
        val imm =
            applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}