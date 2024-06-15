package com.bahrlou.shoppingapp.model.repository.product

import com.bahrlou.shoppingapp.model.data.Ads
import com.bahrlou.shoppingapp.model.data.Product

interface ProductRepository {
    suspend fun getAllProducts(): List<Product>
    suspend fun getAllAds(): List<Ads>
}