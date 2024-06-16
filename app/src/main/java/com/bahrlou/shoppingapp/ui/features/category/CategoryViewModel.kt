package com.bahrlou.shoppingapp.ui.features.category

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.model.repository.product.ProductRepository
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    val productData = mutableStateOf<List<Product>>(listOf())


    fun getProductsByCategory(categoryName: String) {

        viewModelScope.launch {

            val result = productRepository.getProductsByCategory(categoryName)
            productData.value = result
        }

    }
}