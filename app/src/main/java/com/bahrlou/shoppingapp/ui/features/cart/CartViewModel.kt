package com.bahrlou.shoppingapp.ui.features.cart

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.model.repository.cart.CartRepository
import com.bahrlou.shoppingapp.util.coroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    val isNumberChanging = mutableStateOf(Pair("", false))
    val cartList = mutableStateOf(listOf<Product>())
    val totalPrice = mutableIntStateOf(0)

    init {
        getCartInfo()
    }

    private fun getCartInfo() {
        viewModelScope.launch(coroutineExceptionHandler) {

            val data = cartRepository.getCartInfo()

            cartList.value = data.productList
            totalPrice.value = data.totalPrice

        }
    }

    fun addCartItem(productId: String) {

        viewModelScope.launch(coroutineExceptionHandler) {

            isNumberChanging.value = isNumberChanging.value.copy(productId, true)

            val isSuccess = cartRepository.addToCart(productId)
            if (isSuccess) {
                getCartInfo()

            }
            delay(300)

            isNumberChanging.value = isNumberChanging.value.copy(productId, false)

        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch(coroutineExceptionHandler) {

            isNumberChanging.value = isNumberChanging.value.copy(productId, true)

            val isSuccess = cartRepository.removeFromCart(productId)
            if (isSuccess) {
                getCartInfo()

            }
            delay(300)

            isNumberChanging.value = isNumberChanging.value.copy(productId, false)

        }
    }

}