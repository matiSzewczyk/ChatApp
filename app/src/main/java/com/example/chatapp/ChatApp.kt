package com.example.chatapp

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

lateinit var chatApp: App
lateinit var config: RealmConfiguration

class ChatApp : Application(){
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val appId: String = "chatapp-dzbuk"
        chatApp = App(
            AppConfiguration.Builder(appId)
                .build())

        config = RealmConfiguration.Builder()
            .name("appdatabase.db")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
    }


}