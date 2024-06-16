package com.bahrlou.shoppingapp.ui.features.product

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.model.repository.product.ProductRepository
import com.bahrlou.shoppingapp.util.EMPTY_PRODUCT
import com.bahrlou.shoppingapp.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    val product = mutableStateOf<Product>(EMPTY_PRODUCT)

    private fun loadProductFromCache(productId: String) {

        viewModelScope.launch(coroutineExceptionHandler) {
            product.value = productRepository.getProductById(productId)

        }
    }

    fun loadData(productId: String) {
        loadProductFromCache(productId)
    }

}