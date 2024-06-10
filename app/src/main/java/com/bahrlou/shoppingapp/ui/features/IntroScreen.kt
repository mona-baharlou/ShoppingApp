package com.bahrlou.shoppingapp.ui.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import dev.burnoo.cokoin.navigation.getNavController


@Composable
fun IntroScreen() {

    //val navigation = getNavController()
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
            onClick = { }) {
            Text(text = "Sign Up", color = Color.White)
        }


        Button(modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            onClick = {}
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