package com.bahrlou.shoppingapp.ui.features.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bahrlou.shoppingapp.R
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.ui.theme.Blue
import com.bahrlou.shoppingapp.ui.theme.PriceBackground
import com.bahrlou.shoppingapp.ui.theme.Shapes
import com.bahrlou.shoppingapp.util.setPriceFormat
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel
import org.w3c.dom.Text

@Composable
fun CartScreen() {

    val context = LocalContext.current
    val navigation = getNavController()
    val viewModel = getViewModel<CartViewModel>()


}

@Composable
fun CartToolbar(OnBackClicked: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { OnBackClicked.invoke() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null
                )
            }
        }, elevation = 2.dp,
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
        }
    )

@Composable
fun CartList(
    data: List<Product>,
    isNumberChanging: Pair<String, Boolean>,
    OnAddClicked: (String) -> Unit,
    OnRemoveClicked: (String) -> Unit,
    OnItemClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(bottom = 16.dp)
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
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
                                top = 6.dp,
                                bottom = 6.dp,
                                start = 8.dp,
                                end = 8.dp
                            ),
                            text = "$${setPriceFormat((data.price.toInt() * (data.quantity ?: "1").toInt()).toString())}",
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
private fun ProductCount(
    isNumberChanging: Pair<String, Boolean>,
    data: Product
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
private fun RemoveButton(
    data: Product,
    OnRemoveClicked: (String) -> Unit
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
