package com.bahrlou.shoppingapp.model.repository.cart

import com.bahrlou.shoppingapp.model.net.ApiService
import com.google.gson.JsonObject

class CartRepositoryImpl(
    private val apiService: ApiService
) : CartRepository {
    override suspend fun addToCart(productId: String): Boolean {

        val jsonObject = JsonObject().apply {
            addProperty("productId", productId)
        }

        val result = apiService.addProductToCart(jsonObject)

        return result.success

    }


}