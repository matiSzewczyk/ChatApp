package com.example.chatapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.chatapp.realm
import com.example.chatapp.realm.ChatRoom
import io.realm.RealmResults

class RoomMenuViewModel : ViewModel() {

    val roomList = getChatRooms()
    var index: Int = 0

    fun getChatRooms(): RealmResults<ChatRoom> {
        return realm.where(ChatRoom::class.java).findAll()
    }

    fun delete() {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.delete(ChatRoom::class.java)
        }
    }
}