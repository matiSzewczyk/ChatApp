package com.example.chatapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Message(
    @PrimaryKey
    var id: String? = null,
    var messageText: String? = null
) :RealmObject() {
    // add a message row
}