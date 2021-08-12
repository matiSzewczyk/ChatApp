package com.example.chatapp

import android.provider.ContactsContract
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import org.bson.types.ObjectId
import java.net.PasswordAuthentication

open class User(
    @PrimaryKey @RealmField("_id") var _id: Int? = null,
    var email: String? = null,
    var username: String? = null,
    var password: String? = null
) : RealmObject()