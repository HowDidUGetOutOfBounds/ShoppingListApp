package com.example.myapplication.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.model.ShoppingItemRepository
import com.example.myapplication.model.ShoppingPreferences

class ShoppingListViewModelFactory(
    val repository: ShoppingItemRepository,
    val prefs: ShoppingPreferences
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == ShoppingListViewModel::class.java) {
            return ShoppingListViewModel(repository, prefs) as T
        }
        return super.create(modelClass)
    }
}