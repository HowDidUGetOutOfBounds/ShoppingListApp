package com.example.myapplication.model

import com.example.myapplication.data.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingItemRepository {

    fun addItem(itemToAdd: ShoppingItem)

    suspend fun getItemByTitle(title: String): ShoppingItem?

    fun updateItem(itemToUpdate: ShoppingItem)

    fun removeItem(itemToRemove: ShoppingItem)

    fun clearShoppingList()

    fun getAll(): Flow<List<ShoppingItem>>
}