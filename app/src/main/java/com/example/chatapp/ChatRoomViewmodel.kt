package com.example.chatapp

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.mongodb.sync.SyncConfiguration

class ChatRoomViewmodel() : ViewModel() {

    private val partition = _partition
    private val user = chatApp.currentUser()
    private val config = SyncConfiguration.Builder(user, partition).build()
    private val realm = Realm.getInstance(config)
    var chatRoomList = update()

    fun update(): RealmResults<ChatRoom> {
        return realm.where(ChatRoom::class.java).findAll()
    }
    fun createNewRoom(name: String, private: Boolean, password: String): ChatRoom {
        val newRoom = ChatRoom()
        newRoom.name = name
        newRoom.private = private
        newRoom.password = password
        return newRoom
    }

    fun makeNewRoom(room: ChatRoom) {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.copyToRealmOrUpdate(room)
        }
    }

    fun isViable(givenText: String, context: Context): Boolean {
        return if (givenText.isNotEmpty()) {
            if (!givenText.contains(" ")) {
                true
            } else {
                Toast.makeText(context, "Room name and password cannot contain whitespace.", Toast.LENGTH_SHORT).show()
                false
            }
        } else {
            Toast.makeText(context, "Room name and password cannot be empty.", Toast.LENGTH_SHORT).show()
            false
        }
    }

}