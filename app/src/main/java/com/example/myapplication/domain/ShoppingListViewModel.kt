package com.example.myapplication.domain

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.ShoppingItem
import com.example.myapplication.model.ShoppingItemRepository

class ShoppingListViewModel(
    val repository: ShoppingItemRepository
) : ViewModel() {


    fun addItem(item: ShoppingItem) {
        repository.addItem(item)
    }

    fun deleteItem(item: ShoppingItem){
        repository.removeItem(item)
    }

    fun clearShoppingList(){
        repository.clearShoppingList()
    }

    fun getAll(): List<ShoppingItem> {
        return repository.getAll()
    }

}