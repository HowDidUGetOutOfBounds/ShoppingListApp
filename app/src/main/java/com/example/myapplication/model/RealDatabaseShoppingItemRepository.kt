package com.example.myapplication.model

import com.example.myapplication.data.ShoppingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RealDatabaseShoppingItemRepository(
    private val shoppingItemDao: ShoppingItemDao,
    private val databaseCoroutineScope: CoroutineScope
) : ShoppingItemRepository {

    override fun addItem(itemToAdd: ShoppingItem) {
        databaseCoroutineScope.launch {
            shoppingItemDao.insertItem(itemToAdd)
        }
    }

    override suspend fun getItemByTitle(title: String) = shoppingItemDao.getByTitle(title)


    override fun updateItem(itemToUpdate: ShoppingItem) {
        databaseCoroutineScope.launch {
            if (itemToUpdate.amount > 0) {
                shoppingItemDao.updateItem(itemToUpdate)
            } else {
                removeItem(itemToUpdate)
            }
        }
    }

    override fun removeItem(itemToRemove: ShoppingItem) {
        databaseCoroutineScope.launch {
            shoppingItemDao.deleteItem(itemToRemove)
        }
    }

    override fun clearShoppingList() {
        databaseCoroutineScope.launch {
            shoppingItemDao.deleteAll()
        }
    }

    override fun getAll() = shoppingItemDao.getAllItems()
}