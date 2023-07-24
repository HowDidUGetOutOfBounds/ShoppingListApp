package com.example.myapplication.di

import com.example.myapplication.data.ShoppingItem
import com.example.myapplication.model.ShoppingItemRepository
import com.example.myapplication.model.ShoppingItemRepositoryImpl
import com.example.myapplication.utils.Utils.dataSet
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(): ArrayList<ShoppingItem>{
        return dataSet
    }

    @Provides
    fun provideShoppingItemRepository(database: ArrayList<ShoppingItem>): ShoppingItemRepository {
        return ShoppingItemRepositoryImpl(database)
    }
}