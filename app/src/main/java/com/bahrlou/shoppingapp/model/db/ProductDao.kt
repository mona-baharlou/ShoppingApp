package com.bahrlou.shoppingapp.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bahrlou.shoppingapp.model.data.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(products: List<Product>)


    @Query("SELECT * FROM product_table")
    suspend fun getAll() : List<Product>

    @Query("SELECT * FROM product_table WHERE productId = :productId")
    suspend fun getById(productId:Int):Product



}