package com.example.chatapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField

open class User(
    @PrimaryKey @RealmField("_id") var id: String = "",
    var username: String? = null,
    var password: String? = null
) : RealmObject() {
}