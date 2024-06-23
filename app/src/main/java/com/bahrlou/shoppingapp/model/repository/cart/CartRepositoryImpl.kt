package com.bahrlou.shoppingapp.model.repository.cart

import com.bahrlou.shoppingapp.model.data.UserCartInfo
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

    override suspend fun getBadgeNumber(): Int {

        val result = apiService.getUserCart()

        if (result.success) {
            var counter = 0

            result.productList.forEach {
                counter += (it.quantity ?: "0").toInt()
            }

            return counter

        }

        return 0
    }

    override suspend fun removeFromCart(productId: String): Boolean {
        val jsonObject = JsonObject().apply {
            addProperty("productId", productId)
        }

        val result = apiService.removeProductFromCart(jsonObject)

        return result.success

    }

    override suspend fun getCartInfo(): UserCartInfo {

        return apiService.getUserCart()

    }

}