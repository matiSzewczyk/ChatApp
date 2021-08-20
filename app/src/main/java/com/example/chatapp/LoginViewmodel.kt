package com.example.chatapp

import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.mongodb.sync.SyncConfiguration

class LoginViewmodel : ViewModel() {

    private val partition = _partition
    private val user = chatApp.currentUser()
    private val config = SyncConfiguration.Builder(user, partition).build()
    private val realm = Realm.getInstance(config)
    val roomList = getChatRooms()
    var index: Int = 0

    private fun getChatRooms(): RealmResults<ChatRoom> {
        return realm.where(ChatRoom::class.java).findAll()
    }

    fun delete() {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.delete(ChatRoom::class.java)
        }
    }
}