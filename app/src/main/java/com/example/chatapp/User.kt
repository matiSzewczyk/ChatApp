package com.example.chatapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import org.bson.types.ObjectId
import java.util.*

open class User(
    var email: String? = null,
    var password: String? = null
) : RealmObject() {
    @PrimaryKey @RealmField("_id") var _id: String? = UUID.randomUUID().toString()
}