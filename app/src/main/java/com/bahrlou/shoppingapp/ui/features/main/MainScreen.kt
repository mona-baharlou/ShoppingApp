package com.bahrlou.shoppingapp.ui.features.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.bahrlou.shoppingapp.R
import com.bahrlou.shoppingapp.model.data.Ads
import com.bahrlou.shoppingapp.model.data.CheckOut
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.Blue
import com.bahrlou.shoppingapp.ui.theme.CardBackground
import com.bahrlou.shoppingapp.ui.theme.Shapes
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme
import com.bahrlou.shoppingapp.util.CATEGORY
import com.bahrlou.shoppingapp.util.InternetChecker
import com.bahrlou.shoppingapp.util.MyScreens
import com.bahrlou.shoppingapp.util.NO_PAYMENT
import com.bahrlou.shoppingapp.util.PAYMENT_PENDING
import com.bahrlou.shoppingapp.util.PAYMENT_SUCCESS
import com.bahrlou.shoppingapp.util.TAGS
import com.bahrlou.shoppingapp.util.setPriceFormat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel
import org.koin.core.parameter.parametersOf


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ShoppingAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = BackgroundMain
        ) {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {

    val context = LocalContext.current

    ChangeStatusBarColor()

    val viewModel =
        getViewModel<MainViewModel>(parameters = { parametersOf(InternetChecker(context).isInternetConnected) })
    val navigation = getNavController()

    if (InternetChecker(context).isInternetConnected)
        viewModel.getBadgeNumber()


    if (viewModel.getPaymentState() == PAYMENT_PENDING) {
        if (InternetChecker(context).isInternetConnected) {
            viewModel.getCheckoutInfo()
        }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 58.dp)
                .windowInsetsPadding(WindowInsets.statusBars)

        ) {

            SetProgressVisibility(viewModel)

            TopToolbar(
                viewModel.badgeNumber.value,
                onCartClicked = {
                    if (InternetChecker(context).isInternetConnected)
                        navigation.navigate(MyScreens.CartScreen.route)
                    else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.please_check_your_network_connectivity),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                onProfileClicked = {
                    navigation.navigate(MyScreens.ProfileScreen.route)
                })

            CategorySection(CATEGORY) {
                navigation.navigate(MyScreens.CategoryScreen.route + "/" + it)
            }

            val productDataState = viewModel.productData
            val advDataState = viewModel.advData

            SetProductSectionData(TAGS, productDataState.value, advDataState.value) {
                navigation.navigate(MyScreens.ProductScreen.route + "/" + it)

            }

        }

        if (viewModel.showPaymentResultdialog.value) {
            PaymentResultDialog(
                checkoutResult = viewModel.checkoutData.value,
                onDismiss = {
                    viewModel.showPaymentResultdialog.value = false
                    viewModel.setPaymentState(NO_PAYMENTk)
                }
            )
        }
    }
}

/**************************** PaymentResultDialog **********************************/
@Composable
private fun PaymentResultDialog(
    checkoutResult: CheckOut,
    onDismiss: () -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {

        Card(
            elevation = 8.dp,
            shape = Shapes.medium
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Payment Result",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Main Data
                if (checkoutResult.order?.status?.toInt() == PAYMENT_SUCCESS) {

                    AsyncImage(
                        model = R.drawable.success_anim,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(110.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = "Payment was successful!", style = TextStyle(fontSize = 16.sp))
                    Text(
                        text = "Purchase Amount: " + setPriceFormat(
                            (checkoutResult.order!!.amount).substring(
                                0,
                                (checkoutResult.order!!.amount).length - 1
                            )
                        )
                    )

                } else {

                    AsyncImage(
                        model = R.drawable.fail_anim,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(110.dp)
                            .padding(top = 6.dp, bottom = 6.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = "Payment was not successful!", style = TextStyle(fontSize = 16.sp))
                    Text(
                        text = "Purchase Amount: " + setPriceFormat(
                            (checkoutResult.order!!.amount).substring(
                                0,
                                (checkoutResult.order.amount).length - 1
                            )
                        )
                    )

                }

                // Ok Button
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(onClick = onDismiss) {
                        Text(text = "ok")
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                }
            }
        }
    }
}

/**************************************************************************/


@Composable
fun SetProductSectionData(
    tags: List<String>,
    products: List<Product>,
    ads: List<Ads>,
    onProductClicked: (String) -> Unit
) {

    val context = LocalContext.current

    if (products.isNotEmpty()) {
        Column() {

            tags.forEachIndexed { it, _ ->

                val filteredProduct = products.filter { product ->
                    product.tags == tags[it]
                }

                ProductByCategory(tags[it], filteredProduct.shuffled(), onProductClicked)

                if (ads.size >= 2) {
                    if (it == 1 || it == 2) {
                        AdvertisementSection(ads[it - 1], onProductClicked)
                    }
                }

            }
        }
    }
}


@Composable
private fun SetProgressVisibility(viewModel: MainViewModel) {
    if (viewModel.showProgressBar.value) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(), color = Blue
        )
    }
}

//**************************** TOOLBAR **********************************/
@Composable
fun TopToolbar(
    badgeNumber: Int,
    onCartClicked: () -> Unit, onProfileClicked: () -> Unit
) {

    TopAppBar(backgroundColor = Color.White,
        title = { Text(text = "Shopping App") },
        elevation = 0.dp,
        actions = {
            IconButton(
                onClick = { onCartClicked.invoke() }
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

            IconButton(onClick = { onProfileClicked.invoke() }) {
                Icon(Icons.Default.Person, null)
            }
        })

}

//**************************** CATEGORY **********************************/

@Composable
fun CategorySection(categoryList: List<Pair<String, Int>>, onCategoryClicked: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp), contentPadding = PaddingValues(end = 16.dp)
    ) {

        items(categoryList.size) {
            CategoryItem(categoryList[it], onCategoryClicked)
        }
    }
}

@Composable
fun CategoryItem(item: Pair<String, Int>, onCategoryClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onCategoryClicked.invoke(item.first) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = Shapes.medium, color = CardBackground
        ) {

            Image(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = item.second),
                contentDescription = null
            )
        }


        Text(
            text = item.first,
            modifier = Modifier.padding(top = 4.dp),
            style = TextStyle(color = Color.Gray)
        )
    }
}

//**************************** Products **********************************/

@Composable
fun ProductByCategory(tag: String, data: List<Product>, onProductClicked: (String) -> Unit) {

    Column(
        modifier = Modifier.padding(
            top = 32.dp
        )
    ) {

        Text(
            text = tag,
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6
        )

        ProductSection(data, onProductClicked)
    }


}

@Composable
fun ProductSection(data: List<Product>, onProductClicked: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {

        items(data.size) {
            ProductItem(data[it], onProductClicked)
        }

    }
}

@Composable
fun ProductItem(item: Product, onProductClicked: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onProductClicked(item.productId) },
        elevation = 4.dp,
        shape = Shapes.medium
    ) {

        Column {
            AsyncImage(
                modifier = Modifier.size(200.dp),
                model = item.imgUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(10.dp)) {

                Text(
                    text = item.name,
                    style = TextStyle(fontSize = 15.sp),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = setPriceFormat(item.price),
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = item.soldItem + " sold",
                    style = TextStyle(fontSize = 13.sp, color = Color.Gray),
                    modifier = Modifier.padding(top = 2.dp)
                )

            }
        }

    }
}
//**************************** Advertisement Image **********************************/

@Composable
fun AdvertisementSection(adv: Ads, onProductClicked: (String) -> Unit) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
            .clip(shape = Shapes.medium)
            .clickable { onProductClicked.invoke(adv.productId) },
        model = adv.imageURL,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

//************************* STATUS BAR COLOR ****************************************/

@Composable
fun ChangeStatusBarColor() {
    val uiController = rememberSystemUiController()

    SideEffect {
        uiController.setStatusBarColor(Color.White)
    }
}

