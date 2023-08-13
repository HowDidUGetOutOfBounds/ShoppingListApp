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

    val activeItem: MutableLiveData<ShoppingItem?> = MutableLiveData()

    private var _activeItemId: Long? = null
    private var itemToSave: ShoppingItem? = null


    fun setItemToSave(item: ShoppingItem?) {
        itemToSave = item
    }

    fun getItemToSave() = itemToSave
    fun setActiveId(id: Long) {
        _activeItemId = id
    }

    fun getActiveId() = _activeItemId
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

    fun getItemById() {
        viewModelScope.launch {
            val itemId = _activeItemId

            if (itemId != null && _activeItemId != 0L) {
                activeItem.value = repository.getItemById(itemId)
            } else {
                activeItem.value = null
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