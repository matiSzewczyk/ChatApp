package com.example.chatapp

import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.mongodb.sync.SyncConfiguration

class ChatRoomViewmodel() : ViewModel() {

    private val partition = _partition
    private val user = chatApp.currentUser()
    private val config = SyncConfiguration.Builder(user, partition).build()
    private val realm = Realm.getInstance(config)
    val chatRoomList = getChatList(realm)

    private fun getChatList(realm: Realm): RealmResults<ChatRoom> {
        return realm.where(ChatRoom::class.java).findAll()
    }
    fun makeNewRoom(room: ChatRoom) {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.copyToRealmOrUpdate(room)
        }
    }
}