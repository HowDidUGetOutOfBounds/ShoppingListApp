package com.example.myapplication.utils

import com.example.myapplication.R
import com.example.myapplication.data.ShoppingItem

object Utils {

    val dataSet = arrayListOf(
        ShoppingItem(
            image = R.drawable.no_image_placeholder,
            title = "My title 1",
            description = "My Content 1",
            amount = 0
        ),
        ShoppingItem(
            image = null,
            title = "My title 2",
            description = "My Content 2",
            amount = 0
        ),
        ShoppingItem(
            image = R.drawable.no_image_placeholder,
            title = "My title 3",
            description = "My Content 3",
            amount = 0
        ),
        ShoppingItem(
            image = null,
            title = "My title 4",
            description = "My Content 4",
            amount = 0
        )
    )
}