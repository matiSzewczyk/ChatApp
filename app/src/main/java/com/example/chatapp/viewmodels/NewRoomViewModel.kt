package com.example.chatapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.chatapp.realm.ChatRoom
import com.example.chatapp._partition
import com.example.chatapp.chatApp
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration

class NewRoomViewModel : ViewModel() {
    private val partition = _partition
    private val user = chatApp.currentUser()
    private val config = SyncConfiguration.Builder(user, partition).build()
    private val realm = Realm.getInstance(config)

    fun createRoomObject(roomName: String, private: Boolean, password: String) {
        val room = ChatRoom()
        room.name = roomName
        room.isPrivate = private
        room.password = password
        saveRoom(room)
    }
    private fun saveRoom(room: ChatRoom) {
        realm.executeTransactionAsync { bgRealm ->
            _partition = room.name
            bgRealm.copyToRealmOrUpdate(room)
        }
    }

   fun exists(roomName: String): Boolean {
       realm.where(ChatRoom::class.java).equalTo("name", roomName).findFirst()
           ?: return false
       return true
    }
}