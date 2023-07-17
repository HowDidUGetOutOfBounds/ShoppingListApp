package com.example

import android.app.Application
import com.example.myapplication.model.ShoppingItemRepository
import com.example.myapplication.model.ShoppingItemRepositoryImpl
import com.example.myapplication.utils.Utils.dataSet

class MyApp: Application() {

    lateinit var shoppingItemRepository : ShoppingItemRepository
    override fun onCreate() {
        super.onCreate()
        shoppingItemRepository = ShoppingItemRepositoryImpl(database = dataSet)
    }
}