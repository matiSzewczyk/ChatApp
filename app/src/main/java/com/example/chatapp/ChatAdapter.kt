package com.example.chatapp

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import io.realm.RealmResults

class ChatAdapter(
    var users: RealmResults<ChatRoomUsers>,
    var messages: RealmResults<Message>
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageUsername: TextView = itemView.findViewById(R.id.messageUsername)
        val messageText: TextView = itemView.findViewById(R.id.messageText)
        val messageTime: TextView = itemView.findViewById(R.id.messageTime)
        val messageImage: CircleImageView = itemView.findViewById(R.id.messageImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.message_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.apply {
            messageUsername.text = messages[position]?.username
            messageText.text = messages[position]?.message
            messageTime.text = messages[position]?.time
            users.forEach { i ->
                messages.forEach { j ->
                    if (i.id == j.userId) {
                        val convertedImg =
                            BitmapFactory.decodeByteArray(i.image, 0, i.image!!.size)
                        messageImage.setImageBitmap(convertedImg)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}