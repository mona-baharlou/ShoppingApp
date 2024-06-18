package com.bahrlou.shoppingapp.ui.features.main

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahrlou.shoppingapp.model.data.Ads
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.model.repository.cart.CartRepository
import com.bahrlou.shoppingapp.model.repository.product.ProductRepository
import com.bahrlou.shoppingapp.util.coroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val isInternetConnected: Boolean
) : ViewModel() {

    val productData = mutableStateOf<List<Product>>(listOf())
    val advData = mutableStateOf<List<Ads>>(listOf())
    val showProgressBar = mutableStateOf(false)
    val badgeNumber = mutableIntStateOf(0)

    init {
        getDataFromNet(isInternetConnected)
    }

    private fun getDataFromNet(isInternetConnected: Boolean) {

        viewModelScope.launch(coroutineExceptionHandler) {

            if (isInternetConnected) {
                showProgressBar.value = true
            }

            val products = async { productRepository.getAllProducts(isInternetConnected) }
            val advs = async { productRepository.getAllAds(isInternetConnected) }

            updateData(products.await(), advs.await())

            delay(1500)

            showProgressBar.value = false

        }
    }

    private fun updateData(productList: List<Product>, advList: List<Ads>) {

        productData.value = productList
        advData.value = advList

    }

     fun getBadgeNumber() {
        viewModelScope.launch(coroutineExceptionHandler) {
            badgeNumber.value = cartRepository.getBadgeNumber()
        }
    }

}