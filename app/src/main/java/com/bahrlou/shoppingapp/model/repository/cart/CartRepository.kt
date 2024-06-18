package com.bahrlou.shoppingapp.model.repository.cart

import com.bahrlou.shoppingapp.model.data.CartResponse

interface CartRepository {

    suspend fun addToCart(productId: String): Boolean
    suspend fun getBadgeNumber(): Int
}