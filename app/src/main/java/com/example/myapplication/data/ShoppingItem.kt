package com.example.myapplication.data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.adapters.ItemsListAdapter.Companion.VIEW_TYPE_IMAGE_ITEM
import com.example.myapplication.adapters.ItemsListAdapter.Companion.VIEW_TYPE_TEXT_ITEM

@Entity
data class ShoppingItem(
    @PrimaryKey val id: Long,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("image") val image: String?,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("amount") val amount: Int,
): java.io.Serializable