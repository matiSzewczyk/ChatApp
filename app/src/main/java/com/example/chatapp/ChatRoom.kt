package com.example.chatapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.RealmField
import java.util.*

@RealmClass
open class ChatRoom : RealmObject() {
    @PrimaryKey @RealmField("_id")
    var id: String = UUID.randomUUID().toString()

    var name: String = ""

    var isPrivate: Boolean = false

    var password: String = ""

    override fun toString(): String {
        return name
    }

}