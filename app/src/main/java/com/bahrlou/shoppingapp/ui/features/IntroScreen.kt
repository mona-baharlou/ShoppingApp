package com.bahrlou.shoppingapp.ui.features

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.bahrlou.shoppingapp.R
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme
import com.bahrlou.shoppingapp.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController


@Composable
fun IntroScreen() {

    val navigation = getNavController()

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.intro_img),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.78f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = {
                    navigation.navigate(MyScreens.SignUpScreen.route)
            }) {
            Text(text = "Sign Up", color = Color.White)
        }


        Button(modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            onClick = {
                navigation.navigate(MyScreens.SignInScreen.route)
            }
        ) {
            Text(text = "Sign In", color = Color.Blue)
        }

    }

}

@Preview(showBackground = true)
@Composable
fun IntroPreview() {
    ShoppingAppTheme {
        Surface(
            color = BackgroundMain
        ) {
            IntroScreen()
        }
    }
}