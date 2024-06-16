package com.bahrlou.shoppingapp.model.repository.product

import com.bahrlou.shoppingapp.model.data.Ads
import com.bahrlou.shoppingapp.model.data.Product

interface ProductRepository {
    suspend fun getAllProducts(isInternetConnected: Boolean): List<Product>
    suspend fun getAllAds(isInternetConnected: Boolean): List<Ads>

    suspend fun getProductsByCategory(categoryName: String): List<Product>

}