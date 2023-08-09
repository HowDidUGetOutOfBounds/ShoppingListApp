package com.example.myapplication.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.ShoppingItem

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingListDatabase : RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao
}