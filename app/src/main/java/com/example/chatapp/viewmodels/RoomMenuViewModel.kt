package com.example.chatapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.chatapp.realm.ChatRoom
import com.example.chatapp._partition
import com.example.chatapp.chatApp
import io.realm.Realm
import io.realm.RealmResults
import io.realm.mongodb.sync.SyncConfiguration

class RoomMenuViewModel : ViewModel() {

    private val partition = _partition
    private val user = chatApp.currentUser()
    private val config = SyncConfiguration.Builder(user, partition).build()
    private val realm = Realm.getInstance(config)
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