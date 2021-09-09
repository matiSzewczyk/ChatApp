package com.example.chatapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.chatapp._partition
import com.example.chatapp.chatApp
import com.example.chatapp.realm.Message
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.mongodb.sync.SyncConfiguration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatViewModel : ViewModel() {

    private val partition = _partition
    private val user = chatApp.currentUser()
    private val config = SyncConfiguration.Builder(user, partition).build()
    private val realm = Realm.getInstance(config)

    fun createObject(username: String, text: String, time: String, timestamp: String) {
        val message = Message()
        message.username = username
        message.message = text
        message.time = time
        message.timestamp = timestamp
        sendMessage(message)
    }

    private fun sendMessage(currentMsg: Message) {
        realm.executeTransactionAsync { bgRealm ->
            val previousMsg = bgRealm.where(Message::class.java)
                .findAll()
                .sort("timestamp", Sort.ASCENDING)
                .last(null)

            if (previousMsg != null) {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
                val previousMsgTime = LocalDateTime.parse(previousMsg.timestamp, formatter)
                val currentMsgTime = LocalDateTime.parse(currentMsg.timestamp, formatter)

                if (sentWithinTwoMinutes(previousMsgTime, currentMsgTime)
                    && sentBySameUser(previousMsg, currentMsg)) {
                        previousMsg.message = previousMsg.message + "\n${currentMsg.message}"
                        bgRealm.copyToRealmOrUpdate(previousMsg)
                } else {
                    bgRealm.copyToRealmOrUpdate(currentMsg)
                }
            } else {
                bgRealm.copyToRealmOrUpdate(currentMsg)
            }
        }
    }

    private fun sentWithinTwoMinutes(previousMsgTime: LocalDateTime, currentMsgTime: LocalDateTime) : Boolean {
        if (previousMsgTime.isAfter(currentMsgTime.minusMinutes(2)))  {
            return true
        }
        return false
    }

    private fun sentBySameUser(previousMsg: Message, currentMsg: Message) : Boolean {
        if (previousMsg.username == currentMsg.username) {
            return true
        }
        return false
    }

    fun clearDatabase() {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.delete(Message::class.java)
        }
    }

    fun getMessages() :RealmResults<Message> {
        return realm.where(Message::class.java)
            .findAll()
            .sort("timestamp", Sort.ASCENDING)
    }
}