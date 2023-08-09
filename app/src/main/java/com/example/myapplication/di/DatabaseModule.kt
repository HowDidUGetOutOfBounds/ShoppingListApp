package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.ShoppingItem
import com.example.myapplication.model.*
import com.example.myapplication.utils.Utils.dataSet
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(): ArrayList<ShoppingItem> {
        return dataSet
    }

    @Provides
    fun provideRoomDataBase(context: Context): ShoppingListDatabase {
        return Room.databaseBuilder(
            context,
            ShoppingListDatabase::class.java, "ShoppingDatabase"
        ).build()
    }

    @Provides
    fun provideShoppingItemDao(database: ShoppingListDatabase): ShoppingItemDao {
        return database.shoppingItemDao()
    }

    @Provides
    fun provideDatabaseCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @Provides
    fun provideShoppingItemRepository(
        dao: ShoppingItemDao,
        scope: CoroutineScope
    ): ShoppingItemRepository {
        return RealDatabaseShoppingItemRepository(dao, scope)
    }
}