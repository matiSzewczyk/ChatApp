package com.example.chatapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.RealmField
import java.util.*

@RealmClass
open class ProfilePicture : RealmObject() {
    @PrimaryKey @RealmField("_id")
    var id: String = UUID.randomUUID().toString()

    var picture: ByteArray? = null
}