package com.bahrlou.shoppingapp.model.repository.product

import com.bahrlou.shoppingapp.model.data.Ads
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.model.db.ProductDao
import com.bahrlou.shoppingapp.model.net.ApiService

class ProductRepositoryImpl(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductRepository {
    override suspend fun getAllProducts(): List<Product> {

    }

    override suspend fun getAllAds(): List<Ads> {
    }
}