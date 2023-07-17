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
        val item = database.find {
            it.id == itemToUpdate.id
        }
        if (item != null) {
            database.remove(item)
            database.add(itemToUpdate)
        }
    }

    override fun removeItem(itemToRemove: ShoppingItem) {
        database.remove(itemToRemove)
    }

    override fun clearShoppingList() {
        database.clear()
    }

    override fun getAll() = database.toList()

}