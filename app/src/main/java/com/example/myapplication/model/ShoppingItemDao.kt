package com.example.myapplication.model

import androidx.room.*
import com.example.myapplication.data.ShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {
    @Insert
    suspend fun insertItem(item: ShoppingItem)

    @Query("SELECT * FROM shoppingItem")
    fun getAllItems(): Flow<List<ShoppingItem>>

    @Query("SELECT * FROM shoppingItem WHERE id = :inputId LIMIT 1")
    suspend fun getById(inputId: Long): ShoppingItem

    @Query("SELECT * FROM shoppingItem WHERE title = :title LIMIT 1")
    suspend fun getByTitle(title: String): ShoppingItem

    @Update
    suspend fun updateItem(item: ShoppingItem)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)

    @Query("DELETE FROM shoppingItem")
    fun deleteAll()
}