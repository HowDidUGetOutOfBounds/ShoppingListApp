package com.example.myapplication.domain

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ShoppingItem
import com.example.myapplication.model.ShoppingItemRepository
import com.example.myapplication.model.ShoppingPreferences
import kotlinx.coroutines.launch

class ShoppingListViewModel(
    private val repository: ShoppingItemRepository,
    private val prefs: ShoppingPreferences
) : ViewModel() {

    val databaseData: MutableLiveData<List<ShoppingItem>> = MutableLiveData()
    val lastImageUriData: MutableLiveData<Uri> = MutableLiveData()

    fun addItem(item: ShoppingItem) {
        repository.addItem(item)
    }

    fun updateItem(item: ShoppingItem) {
        repository.updateItem(item)
    }

    fun deleteItem(item: ShoppingItem) {
        repository.removeItem(item)
    }

    fun clearShoppingList() {
        repository.clearShoppingList()
    }

    fun getAll() {
        viewModelScope.launch {
            repository.getAll().collect { newList ->
                databaseData.value = newList
            }
        }
    }

    fun setImageUri(imageUri: Uri) {
        lastImageUriData.value = imageUri
    }

    fun saveCity(city: String) {
        prefs.setCity(city)
    }

    fun loadCity() = prefs.getCity()
}