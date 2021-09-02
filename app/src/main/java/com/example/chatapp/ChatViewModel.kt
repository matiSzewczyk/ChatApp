package com.example.chatapp

import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.Sort
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatViewModel : ViewModel() {
    fun createObject(username: String, text: String, time: String, timestamp: String, userId: String) : Message {
        val message = Message()
        message.username = username
        message.message = text
        message.time = time
        message.timestamp = timestamp
        message.userId = userId
        return message
    }

    fun sendMessage(realm: Realm, currentMsg: Message) {
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

    fun clearDatabase(realm: Realm) {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.delete(Message::class.java)
        }
    }

    fun addNewUser(realm: Realm, image: ByteArray?, userId: String) {
        val newUser = ChatRoomUsers()
        newUser.image = image
        newUser.id = userId
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.copyToRealmOrUpdate(newUser)
        }
    }
}