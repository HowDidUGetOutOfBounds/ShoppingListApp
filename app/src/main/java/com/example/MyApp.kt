package com.example

import android.app.Application
import com.example.myapplication.di.AppContextModule
import com.example.myapplication.di.DaggerDatabaseComponent
import com.example.myapplication.di.DatabaseComponent
import com.example.myapplication.di.DatabaseModule

class MyApp : Application() {

    lateinit var databaseComponent: DatabaseComponent
    override fun onCreate() {
        super.onCreate()
        databaseComponent = DaggerDatabaseComponent.builder()
            .appContextModule(AppContextModule(this))
            .databaseModule(DatabaseModule())
            .build()
    }
}