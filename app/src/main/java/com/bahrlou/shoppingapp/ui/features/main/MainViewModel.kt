package com.bahrlou.shoppingapp.ui.features.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahrlou.shoppingapp.model.data.Ads
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.model.repository.product.ProductRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(
    private val productRepository: ProductRepository,
    private val isInternetConnected: Boolean
) : ViewModel() {

    val productData = mutableStateOf<List<Product>>(listOf())
    val advData = mutableStateOf<List<Ads>>(listOf())
    val showProgressBar = mutableStateOf(false)

    init {
        getDataFromNet(isInternetConnected)
    }

    private fun getDataFromNet(isInternetConnected: Boolean) {
        viewModelScope.launch {

            if (isInternetConnected) {
                showProgressBar.value = true
            }

            val products = async { productRepository.getAllProducts(isInternetConnected) }
            val advs = async { productRepository.getAllAds(isInternetConnected) }

            updateData(products.await(), advs.await())

            showProgressBar.value = false

        }
    }

    private fun updateData(productList: List<Product>, advList: List<Ads>) {

        productData.value = productList
        advData.value = advList

    }

}