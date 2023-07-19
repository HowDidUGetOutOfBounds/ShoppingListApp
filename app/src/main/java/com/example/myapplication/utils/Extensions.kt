package com.example.myapplication.utils

import com.example.myapplication.adapters.ItemsListAdapter
import com.example.myapplication.data.ShoppingItem


fun ShoppingItem.getViewType(): Int {
    return if (image != null) {
        ItemsListAdapter.VIEW_TYPE_IMAGE_ITEM
    } else {
        ItemsListAdapter.VIEW_TYPE_TEXT_ITEM
    }
}