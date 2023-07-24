package com.example

import android.app.Application
import com.example.myapplication.di.DaggerDatabaseComponent
import com.example.myapplication.di.DatabaseComponent
import com.example.myapplication.model.ShoppingItemRepository
import com.example.myapplication.model.ShoppingItemRepositoryImpl
import com.example.myapplication.utils.Utils.dataSet

class MyApp: Application() {

    lateinit var databaseComponent: DatabaseComponent
    override fun onCreate() {
        super.onCreate()
        databaseComponent = DaggerDatabaseComponent.create()
    }
}