package com.example.myapplication.model

import com.example.myapplication.data.ShoppingItem

interface ShoppingItemRepository {

    fun addItem(itemToAdd: ShoppingItem)

    fun getItemByTitle(title: String): ShoppingItem?

    fun updateItem(itemToUpdate: ShoppingItem)

    fun removeItem(itemToRemove: ShoppingItem)

    fun clearShoppingList()

    fun getAll(): List<ShoppingItem>
}