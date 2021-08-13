package com.example.chatapp

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.sync.SyncConfiguration

lateinit var chatApp: App
lateinit var config: RealmConfiguration

class ChatApp : Application(){
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val appId: String = "chatapp-qwakq"
        chatApp = App(
            AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID)
                .build())

    }


}