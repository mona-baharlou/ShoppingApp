package com.bahrlou.shoppingapp.ui.features.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ShoppingAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BackgroundMain
        ) {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {


}