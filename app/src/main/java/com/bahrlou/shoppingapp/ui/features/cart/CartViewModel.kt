package com.bahrlou.shoppingapp.ui.features.cart

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.model.repository.cart.CartRepository
import com.bahrlou.shoppingapp.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    val isNumberChanging = mutableStateOf(Pair("", false))
    val cartList = mutableStateOf(listOf<Product>())
    val totalPrice = mutableIntStateOf(0)

    fun getCartInfo() {
        viewModelScope.launch(coroutineExceptionHandler) {

            val data = cartRepository.getCartInfo()

            cartList.value = data.productList
            totalPrice.value = data.totalPrice

        }
    }

    fun addCartItem(productId:String){
        viewModelScope.launch (coroutineExceptionHandler){
            cartRepository.addToCart(productId)
        }
    }

    fun removeFromCart(productId: String){
        viewModelScope.launch (coroutineExceptionHandler){
            cartRepository.removeFromCart(productId)
        }
    }
}