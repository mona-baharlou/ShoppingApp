package com.bahrlou.shoppingapp.ui.features.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bahrlou.shoppingapp.R
import com.bahrlou.shoppingapp.model.data.Ads
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.Blue
import com.bahrlou.shoppingapp.ui.theme.CardBackground
import com.bahrlou.shoppingapp.ui.theme.Shapes
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme
import com.bahrlou.shoppingapp.util.CATEGORY
import com.bahrlou.shoppingapp.util.InternetChecker
import com.bahrlou.shoppingapp.util.TAGS
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)

    ) {

        SetProgressVisibility(viewModel)

        TopToolbar()

        CategorySection(CATEGORY)

        val productDataState = viewModel.productData
        val advDataState = viewModel.advData

        SetProductSectionData(TAGS, productDataState.value, advDataState.value)


        /* CategorySection()
         ProductByCategory()//subject
         ProductByCategory()
         AdvertisementSection()
         ProductByCategory()
         ProductByCategory()*/
    }

}

@Composable
fun SetProductSectionData(tags: List<String>, products: List<Product>, ads: List<Ads>) {

    Column() {

        tags.forEachIndexed { it, _ ->

            val filteredProduct = products.filter { product ->
                product.tags == tags[it]
            }

            ProductByCategory(tags[it], filteredProduct.shuffled())

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
fun TopToolbar() {

    TopAppBar(backgroundColor = Color.White,
        title = { Text(text = "Shopping App") },
        elevation = 0.dp,
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.ShoppingCart, null)
            }

            IconButton(onClick = {}) {
                Icon(Icons.Default.Person, null)
            }
        })

}

//**************************** CATEGORY **********************************/

@Composable
fun CategorySection(categoryList: List<Pair<String, Int>>) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp), contentPadding = PaddingValues(end = 16.dp)
    ) {

        items(categoryList.size) {
            CategoryItem(categoryList[it])
        }
    }
}

@Composable
fun CategoryItem(item: Pair<String, Int>) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable {},
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
fun ProductByCategory(tag: String, data: List<Product>) {

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

        ProductSection(data)
    }


}

@Composable
fun ProductSection(data: List<Product>) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {

        items(data.size) {
            ProductItem(data[it])
        }

    }
}

@Composable
fun ProductItem(item: Product) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { },
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
                    text = "$" + item.price,
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
fun AdvertisementSection() {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
            .clip(shape = Shapes.medium)
            .clickable { },
        painter = painterResource(id = R.drawable.img_intro),
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

