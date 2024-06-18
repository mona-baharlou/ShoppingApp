package com.bahrlou.shoppingapp.ui.features.product

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahrlou.shoppingapp.model.data.Comment
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.model.repository.cart.CartRepository
import com.bahrlou.shoppingapp.model.repository.comment.CommentRepository
import com.bahrlou.shoppingapp.model.repository.product.ProductRepository
import com.bahrlou.shoppingapp.util.EMPTY_PRODUCT
import com.bahrlou.shoppingapp.util.coroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val commentRepository: CommentRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    val product = mutableStateOf<Product>(EMPTY_PRODUCT)
    val comments = mutableStateOf(listOf<Comment>())
    val isProductAdding = mutableStateOf(false)

    private fun loadProductFromCache(productId: String) {

        viewModelScope.launch(coroutineExceptionHandler) {
            product.value = productRepository.getProductById(productId)
        }
    }

    fun loadData(productId: String, isInternetConnected: Boolean) {
        loadProductFromCache(productId)

        if (isInternetConnected)
            getComments(productId)
    }

    private fun getComments(productId: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            comments.value = commentRepository.getComments(productId)
        }
    }

    fun addComment(
        productId: String,
        comment: String,
        IsSucceed: (String) -> Unit
    ) {
        viewModelScope.launch(coroutineExceptionHandler) {

            commentRepository.addComment(productId, comment, IsSucceed)

            delay(100)

            comments.value = commentRepository.getComments(productId)
        }
    }

    fun addProductToCart(productId: String, addingToCartResult: (String) -> Unit) {
        viewModelScope.launch(coroutineExceptionHandler) {
            isProductAdding.value = true
            val result = cartRepository.addToCart(productId)
            delay(500)
            isProductAdding.value = false

            if(result){
                addingToCartResult.invoke("Product is added to cart")
            }
            else{
                addingToCartResult.invoke("Product does not added to cart!")
            }
        }
    }
}