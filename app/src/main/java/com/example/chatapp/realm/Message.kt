package com.example.chatapp.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.RealmField
import java.util.*

@RealmClass
open class Message : RealmObject() {
    @PrimaryKey
    @RealmField("_id")
    var id: String = UUID.randomUUID().toString()

    var username: String = ""

    var message: String = ""

    var time: String = ""

    var timestamp: String = ""

}