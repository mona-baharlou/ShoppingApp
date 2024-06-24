package com.bahrlou.shoppingapp.model.repository.cart

import android.content.SharedPreferences
import com.bahrlou.shoppingapp.model.data.CheckOut
import com.bahrlou.shoppingapp.model.data.SubmitOrder
import com.bahrlou.shoppingapp.model.data.UserCartInfo
import com.bahrlou.shoppingapp.model.net.ApiService
import com.bahrlou.shoppingapp.util.NO_PAYMENT
import com.google.gson.JsonObject

class CartRepositoryImpl(
    private val apiService: ApiService,
    private val sharedPref: SharedPreferences
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

    override suspend fun submitOrder(address: String, postalCode: String): SubmitOrder {
        val jsonObject = JsonObject().apply {
            addProperty("address", address)
            addProperty("postalCode", postalCode)

        }

        val result = apiService.submitOrder(jsonObject)
        setOrderId(result.orderId.toString())
        return result
    }

    override suspend fun checkout(orderId: String): CheckOut {
        val jsonObject = JsonObject().apply {
            addProperty("orderId", orderId)
        }

        val result = apiService.checkout(jsonObject)
        return result
    }

    override fun setOrderId(orderId: String) {
        sharedPref.edit().putString("orderId", orderId).apply()
    }

    override fun getOrderId(): String {
        return sharedPref.getString("orderId", "0")!!
    }

    override fun setPaymentState(status: Int) {
        sharedPref.edit().putInt("purchase_state", status).apply()
    }

    override fun getPaymentState(): Int {
        return sharedPref.getInt("purchase_state", NO_PAYMENT)
    }

}