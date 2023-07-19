package com.example.myapplication.model

import com.example.myapplication.data.ShoppingItem

class ShoppingItemRepositoryImpl(
    val database: ArrayList<ShoppingItem>
) : ShoppingItemRepository {
    override fun addItem(itemToAdd: ShoppingItem) {
        database.add(itemToAdd)
    }

    override fun getItemByTitle(title: String): ShoppingItem? {
        return database.find {
            it.title == title
        }
    }

    override fun updateItem(itemToUpdate: ShoppingItem) {
        val itemIndex = database.indexOfFirst { it.id == itemToUpdate.id }
        if (itemIndex != -1) {
            if (itemToUpdate.amount > 0) {
                database[itemIndex] = itemToUpdate
            } else {
                removeItem(itemToUpdate)
            }
        }
    }

    override fun removeItem(itemToRemove: ShoppingItem) {
        val itemIndex = database.indexOfFirst { it.id == itemToRemove.id }
        if (itemIndex != -1) {
            database.removeAt(itemIndex)
        }
    }

    override fun clearShoppingList() {
        database.clear()
    }

    override fun getAll() = database.toList()

}