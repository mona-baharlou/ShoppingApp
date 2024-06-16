package com.bahrlou.shoppingapp.ui.features.product

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bahrlou.shoppingapp.R
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.Shapes
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme
import com.bahrlou.shoppingapp.util.InternetChecker
import com.bahrlou.shoppingapp.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel


@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    ShoppingAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = BackgroundMain
        ) {
            //ProductScreen("")
        }
    }
}

@Composable
fun ProductScreen(
    productId: String, onBackClicked: () -> Unit,
    onCartClicked: () -> Unit
) {

    val context = LocalContext.current
    val navigation = getNavController()
    val viewModel = getViewModel<ProductViewModel>()
    viewModel.loadData(productId)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 58.dp),

            ) {

            ProductToolbar(
                name = "Details",
                badgeNumber = 4,
                onBackClicked = {
                    navigation.popBackStack()
                },
                onCartClicked = {
                    if (InternetChecker(context).isInternetConnected) {
                        navigation.navigate(MyScreens.CartScreen.route)
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.please_check_your_network_connectivity),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )

            ProductItem(data = viewModel.product.value) {

            }


        }


        AddToCart()

    }

}

@Composable
fun ProductItem(data: Product, OnCategoryClicked: (String) -> Unit) {

    Column(
        modifier = Modifier.padding()
    ) {
        ProductInfoSection(data, OnCategoryClicked)

    }
}

@Composable
fun ProductInfoSection(product: Product, OnCategoryClicked: (String) -> Unit) {

    AsyncImage(
        model = product.imgUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(Shapes.medium)
    )


    Text(
        text = product.name,
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(top = 14.dp)
    )


    Text(
        text = product.detailText,
        style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Justify),
        modifier = Modifier.padding(top = 4.dp),
    )


    TextButton(
        onClick = { OnCategoryClicked.invoke(product.category) },
        modifier = Modifier.padding(top = 4.dp),
    ) {
        Text(
            text = "#" + product.category,
            style = TextStyle(fontSize = 13.sp),
        )
    }
}

@Composable
fun ProductToolbar(
    name: String,
    badgeNumber: Int,
    onBackClicked: () -> Unit,
    onCartClicked: () -> Unit
) {
    TopAppBar(
        elevation = 2.dp,
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                text = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = 24.dp
                    ),
                textAlign = TextAlign.Center
            )
        },

        actions = {
            IconButton(
                onClick = { onCartClicked.invoke() },
                modifier = Modifier.padding(end = 6.dp)
            ) {
                if (badgeNumber == 0) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null)
                } else {
                    BadgedBox(badge = {
                        Badge {
                            Text(text = badgeNumber.toString())
                        }
                    }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    }
                }
            }
        },

        navigationIcon = {

            IconButton(onClick = { onBackClicked.invoke() }) {

                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null
                )

            }
        }


    )
}

@Composable
fun AddToCart() {

}
