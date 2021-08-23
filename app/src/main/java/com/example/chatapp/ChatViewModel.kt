package com.example.chatapp

import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.Sort
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ChatViewModel : ViewModel() {
    fun createObject(username: String, text: String, time: String, timestamp: String) : Message {
        val message = Message()
        message.username = username
        message.message = text
        message.time = time
        message.timestamp = timestamp
        return message
    }

    fun sendMessage(realm: Realm, message: Message) {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.copyToRealmOrUpdate(message)
        }
    }

    fun getPreviousMessage(realm: Realm, username: String, msg: Message, message: String) {
        realm.executeTransactionAsync { bgRealm ->
            val prevMsg = bgRealm.where(Message::class.java)
                .findAll()
                .sort("timestamp", Sort.ASCENDING)
                .last(null)
            println("-------------\nprev msg: ${prevMsg!!.message}\n---------------")

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
            val previousMsg = LocalDateTime.parse(prevMsg!!.timestamp, formatter)
            val currentMsg = LocalDateTime.parse(msg.timestamp, formatter)

            if (previousMsg.isAfter(currentMsg.minusMinutes(2))) {
                prevMsg.message = prevMsg.message + "\n$message"
                println("works")
            } else {
                bgRealm.copyToRealmOrUpdate(msg)
//                sendMessage(realm, msg)
            }
        }
    }

    fun clearDatabase(realm: Realm) {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.delete(Message::class.java)
        }
    }
}