package com.example.chatapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.chatapp._partition
import com.example.chatapp.realm
import com.example.chatapp.realm.ChatRoom

class NewRoomViewModel : ViewModel() {

    fun createRoomObject(roomName: String, private: Boolean, password: String) {
        val room = ChatRoom()
        room.name = roomName
        room.isPrivate = private
        room.password = password
        saveRoom(room)
    }
    private fun saveRoom(room: ChatRoom) {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.copyToRealmOrUpdate(room)
            _partition = room.name
        }
    }

   fun exists(roomName: String): Boolean {
       realm.where(ChatRoom::class.java).equalTo("name", roomName).findFirst()
           ?: return false
       return true
    }
}