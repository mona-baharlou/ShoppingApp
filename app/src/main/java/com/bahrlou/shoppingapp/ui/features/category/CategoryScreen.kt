package com.bahrlou.shoppingapp.ui.features.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {

}

@Composable
fun CategoryScreen(categoryName: String) {

    val navigation = getNavController()

    val viewModel = getViewModel<CategoryViewModel>()
    viewModel.getProductsByCategory(categoryName)


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        CategoryToolbar(categoryName)

        val productList = viewModel.productData
        Products(productList.value) {
            navigation.navigate(MyScreens.ProductScreen.route + "/" + it)
        }
    }

}

@Composable
fun Products(productList: List<Product>, onProductClicked: (String) -> Unit) {


}

@Composable
fun ProductItem(data: Product, onProductClicked: (String) -> Unit) {


}

@Composable
fun CategoryToolbar(categoryName: String) {

}
