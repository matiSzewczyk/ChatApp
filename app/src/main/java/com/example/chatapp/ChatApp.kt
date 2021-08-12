package com.example.chatapp

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

lateinit var chatApp: App

class ChatApp : Application(){
    override fun onCreate() {
        super.onCreate()

        val appId: String = "chatapp-dzbuk"
        Realm.init(this)
        chatApp = App(
            AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID)
                .build())

        val config = RealmConfiguration.Builder()
            .name("appdatabase.db")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
    }


}