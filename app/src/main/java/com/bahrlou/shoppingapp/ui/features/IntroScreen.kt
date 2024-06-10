package com.bahrlou.shoppingapp.ui.features

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bahrlou.shoppingapp.ui.screen.ShoppingUi
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme


@Composable
fun IntroScreen() {


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    Surface(
        color = BackgroundMain, modifier = Modifier.fillMaxSize()
    ) {

        IntroScreen()
    }
}