package com.bahrlou.shoppingapp.ui.features.cart

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bahrlou.shoppingapp.R
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.ui.features.profile.AddUserLocationDialog
import com.bahrlou.shoppingapp.ui.theme.Blue
import com.bahrlou.shoppingapp.ui.theme.PriceBackground
import com.bahrlou.shoppingapp.ui.theme.Shapes
import com.bahrlou.shoppingapp.util.CLICK_TO_ADD
import com.bahrlou.shoppingapp.util.InternetChecker
import com.bahrlou.shoppingapp.util.MyScreens
import com.bahrlou.shoppingapp.util.PAYMENT_PENDING
import com.bahrlou.shoppingapp.util.setPriceFormat
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel

@Composable
fun CartScreen() {

    val context = LocalContext.current
    val dialogState = remember { mutableStateOf(false) }
    val navigation = getNavController()
    val viewModel = getViewModel<CartViewModel>()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        contentAlignment = Alignment.BottomCenter,
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 75.dp)
        ) {

            CartToolbar(OnBackClicked = {
                navigation.popBackStack()
            }, OnProfileClicked = {
                navigation.navigate(MyScreens.ProfileScreen.route)
            })


            if (viewModel.cartList.value.isNotEmpty()) {
                CartList(
                    data = viewModel.cartList.value,
                    isNumberChanging = viewModel.isNumberChanging.value,
                    OnAddClicked = {
                        viewModel.addCartItem(productId = it)
                    },
                    OnRemoveClicked = {
                        viewModel.removeFromCart(productId = it)

                    },
                    OnItemClicked = {
                        navigation.navigate(MyScreens.ProductScreen.route + "/${it}")
                    }
                )
            } else {
                NoDataAnimation()
            }
        }

        Purchase(totalPrice = viewModel.totalPrice.value.toString()) {

            if (viewModel.cartList.value.isNotEmpty()) {

                val userLocation = viewModel.getUserLocation()

                if (userLocation.first == CLICK_TO_ADD || userLocation.second == CLICK_TO_ADD) {
                    dialogState.value = true
                } else {

                    //go for payment
                    GoForPayment(viewModel, userLocation, context)
                }


            } else {
                Toast.makeText(
                    context,
                    "Please add some products to cart first",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        if (dialogState.value) {
            AddUserLocationDialog(showSaveLocation = true,
                OnDismiss = {
                    dialogState.value = false
                },
                OnSubmitClicked = { address, postalCode, isChecked ->

                    if (InternetChecker(context).isInternetConnected) {

                        if (isChecked) {
                            viewModel.saveUserLocation(address, postalCode)
                        }
                        val userLocation = viewModel.getUserLocation()

                        GoForPayment(viewModel, userLocation, context)

                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.please_check_your_network_connectivity),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
        }

    }


}


fun GoForPayment(
    viewModel: CartViewModel,
    userLocation: Pair<String, String>,
    context: Context
) {


    viewModel.purchase(userLocation.first, userLocation.second) { success, link ->

        if (success) {
            Toast.makeText(context, "Pay using ZarrinPal", Toast.LENGTH_SHORT)
                .show()

            viewModel.setPaymentState(PAYMENT_PENDING)

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            context.startActivity(intent)


        } else {
            Toast.makeText(context, "Problem in payment", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun NoDataAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.no_data)
    )

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

}

@Composable
fun CartToolbar(
    OnBackClicked: () -> Unit, OnProfileClicked: () -> Unit
) {
    TopAppBar(navigationIcon = {
        IconButton(onClick = { OnBackClicked.invoke() }) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null
            )
        }
    },
        elevation = 2.dp,
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                text = "My Cart",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 58.dp)
            )
        },
        actions = {

            IconButton(
                modifier = Modifier.padding(end = 6.dp),
                onClick = { OnProfileClicked.invoke() }) {
                Icon(Icons.Default.Person, contentDescription = null)

            }
        })
}

@Composable
fun CartList(
    data: List<Product>,
    isNumberChanging: Pair<String, Boolean>,
    OnAddClicked: (String) -> Unit,
    OnRemoveClicked: (String) -> Unit,
    OnItemClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(data.size) {
            CartItem(
                data = data[it],
                isNumberChanging = isNumberChanging,
                OnAddClicked = OnAddClicked,
                OnRemoveClicked = OnRemoveClicked,
                OnItemClicked = OnItemClicked
            )
        }
    }
}


@Composable
fun CartItem(
    data: Product,
    isNumberChanging: Pair<String, Boolean>,
    OnAddClicked: (String) -> Unit,
    OnRemoveClicked: (String) -> Unit,
    OnItemClicked: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable { OnItemClicked.invoke(data.productId) },
        elevation = 4.dp,
        shape = Shapes.large
    ) {

        Column() {
            AsyncImage(
                model = data.imgUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(10.dp)) {

                    Text(
                        text = data.name,
                        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Medium)
                    )

                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "From ${data.category} group",
                        style = TextStyle(fontSize = 14.sp)
                    )

                    Text(
                        modifier = Modifier.padding(top = 18.dp),
                        text = "Product authenticity guarantee",
                        style = TextStyle(fontSize = 14.sp)
                    )

                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "Available in stock to ship",
                        style = TextStyle(fontSize = 14.sp)
                    )

                    Surface(
                        modifier = Modifier
                            .padding(top = 18.dp, bottom = 6.dp)
                            .clip(Shapes.large),
                        color = PriceBackground
                    ) {


                        Text(
                            modifier = Modifier.padding(
                                top = 6.dp, bottom = 6.dp, start = 8.dp, end = 8.dp
                            ),
                            text = "${setPriceFormat((data.price.toInt() * (data.quantity ?: "1").toInt()).toString())}",
                            style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        )


                    }
                }

                Surface(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 14.dp)
                        .align(Alignment.Bottom)
                ) {
                    Card(border = BorderStroke(width = 2.dp, color = Blue)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            //Minus/Trash Button
                            RemoveButton(data, OnRemoveClicked)

                            //Product Count
                            ProductCount(isNumberChanging, data)

                            //Add Button
                            IconButton(onClick = { OnAddClicked.invoke(data.productId) }) {
                                Icon(
                                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                            }

                        }

                    }
                }

            }


        }

    }
}

@Composable
fun ProductCount(
    isNumberChanging: Pair<String, Boolean>, data: Product
) {
    if (isNumberChanging.first == data.productId && isNumberChanging.second) {

        Text(
            text = "...",
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.padding(bottom = 12.dp)
        )

    } else {
        Text(
            text = data.quantity ?: "1",
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}

@Composable
fun RemoveButton(
    data: Product, OnRemoveClicked: (String) -> Unit
) {
    if (data.quantity?.toInt() == 1) {
        IconButton(onClick = { OnRemoveClicked.invoke(data.productId) }) {
            Icon(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
    } else {
        IconButton(onClick = { OnRemoveClicked.invoke(data.productId) }) {
            Icon(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                painter = painterResource(id = R.drawable.ic_minus),
                contentDescription = null
            )
        }
    }
}

/************************** PURChASE ***************************/
@Composable
fun Purchase(
    totalPrice: String,
    OnPurchaseClicked: () -> Unit
) {
    val context = LocalContext.current

    val config = LocalConfiguration.current
    val fraction = if (config.orientation == Configuration.ORIENTATION_LANDSCAPE)
        0.15f else 0.07f


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction), color = Color.White
    )
    {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(182.dp, 40.dp),
                onClick = {
                    if (InternetChecker(context = context).isInternetConnected) {
                        OnPurchaseClicked.invoke()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.please_check_your_network_connectivity),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }) {

                Text(
                    text = "Let's purchase",
                    modifier = Modifier.padding(2.dp),
                    color = Color.White,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                )

            }

            Surface(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clip(Shapes.large),
                color = PriceBackground
            ) {

                Text(
                    text = setPriceFormat(totalPrice),
                    modifier = Modifier.padding(
                        top = 6.dp,
                        bottom = 6.dp,
                        start = 8.dp,
                        end = 8.dp
                    ),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }

}



