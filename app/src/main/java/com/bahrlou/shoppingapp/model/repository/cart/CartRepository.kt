package com.bahrlou.shoppingapp.model.repository.cart

import com.bahrlou.shoppingapp.model.data.CartResponse
import com.bahrlou.shoppingapp.model.data.CheckOut
import com.bahrlou.shoppingapp.model.data.SubmitOrder
import com.bahrlou.shoppingapp.model.data.UserCartInfo

interface CartRepository {

    suspend fun addToCart(productId: String): Boolean
    suspend fun getBadgeNumber(): Int
    suspend fun removeFromCart(productId: String): Boolean
    suspend fun getCartInfo(): UserCartInfo



    suspend fun submitOrder(address: String, postalCode: String): SubmitOrder
    suspend fun checkout(orderId: String): CheckOut


    suspend fun setOrderId(orderId: String)
    suspend fun getOrderId():String


    suspend fun setPurchaseState(status: Int)
    suspend fun getPurchaseState():Int

}
