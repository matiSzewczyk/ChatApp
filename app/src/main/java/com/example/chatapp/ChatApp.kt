package com.example.chatapp

import android.app.Application
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

lateinit var chatApp: App
lateinit var _partition: String

class ChatApp : Application(){
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        chatApp = App(
            AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID)
                .build()
        )
    }
}