package com.example.chatapp

import androidx.lifecycle.ViewModel
import io.realm.Realm

class ChatViewModel : ViewModel() {
    fun sendMessage(realm: Realm, message: Message) {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.copyToRealmOrUpdate(message)
        }
    }
    fun createObject(username: String, text: String, time: String, timestamp: String) : Message {
        val message = Message()
        message.username = username
        message.message = text
        message.time = time
        message.timestamp = timestamp
        return message
    }
    fun clearDatabase(realm: Realm) {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.delete(Message::class.java)
        }
    }
}