package com.bahrlou.shoppingapp.model.repository.cart

import com.bahrlou.shoppingapp.model.data.CartResponse
import com.bahrlou.shoppingapp.model.data.UserCartInfo

interface CartRepository {

    suspend fun addToCart(productId: String): Boolean
    suspend fun getBadgeNumber(): Int
    suspend fun removeFromCart(productId: String): Boolean
    suspend fun getCartInfo(): UserCartInfo
}