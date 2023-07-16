package com.example.myapplication.data

import com.example.myapplication.adapters.ItemsListAdapter.Companion.VIEW_TYPE_IMAGE_ITEM
import com.example.myapplication.adapters.ItemsListAdapter.Companion.VIEW_TYPE_TEXT_ITEM

data class ShoppingItem(
    val title: String,
    val image: Int?,
    val description: String,
    val amount: Int,
) {
    fun getViewType(): Int {
        return if (image != null) {
            VIEW_TYPE_IMAGE_ITEM
        } else {
            VIEW_TYPE_TEXT_ITEM
        }
    }
}