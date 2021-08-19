package com.example.chatapp

import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration

class NewRoomViewmodel : ViewModel() {
    private val partition = _partition
    private val user = chatApp.currentUser()
    private val config = SyncConfiguration.Builder(user, partition).build()
    private val realm = Realm.getInstance(config)

    fun makeNewRoom(room: ChatRoom) {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.copyToRealmOrUpdate(room)
        }
    }

   fun exists(roomName: String): Boolean {
       realm.where(ChatRoom::class.java).equalTo("name", roomName).findFirst()
           ?: return false
       return true
    }
}