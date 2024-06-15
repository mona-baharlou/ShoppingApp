package com.bahrlou.shoppingapp.ui.features.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bahrlou.shoppingapp.R
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.CardBackground
import com.bahrlou.shoppingapp.ui.theme.Shapes
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


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

    ChangeStatusBarColor()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)

    ) {
        TopToolbar()
        CategorySection()
        ProductByCategory()//subject
        ProductByCategory()
        AdvPic()
        ProductByCategory()
        ProductByCategory()
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
fun CategorySection() {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp), contentPadding = PaddingValues(end = 16.dp)
    ) {

        items(10) {
            CategoryItem()
        }
    }
}

@Composable
fun CategoryItem() {
    Column(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable {},
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = Shapes.medium,
            color = CardBackground
        ) {
            Image(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = R.drawable.ic_icon_app),
                contentDescription = null
            )
        }


        Text(
            text = "Bags", modifier = Modifier
                .padding(top = 4.dp),
            style = TextStyle(color = Color.Gray)
        )
    }
}

//**************************** Products **********************************/

@Composable
fun ProductByCategory() {

}
//**************************** Advertisement Image **********************************/

@Composable
fun AdvPic() {

}

@Composable
fun ChangeStatusBarColor() {
    val uiController = rememberSystemUiController()

    SideEffect {
        uiController.setStatusBarColor(Color.White)
    }
}

