package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    var messages: MutableList<Message>,
    private val myInterface: ChatInterface
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.messageText)
        val messageTime: TextView = itemView.findViewById(R.id.messageTime)
        private val sendMsgBtn: Button = itemView.findViewById(R.id.sendMsgBtn)

        init {
            apply {
                sendMsgBtn.setOnClickListener {
                    myInterface.chatClickListener(absoluteAdapterPosition, itemView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.apply {
            messageText.text = messages[position].message
            messageTime.text = messages[position].time
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}