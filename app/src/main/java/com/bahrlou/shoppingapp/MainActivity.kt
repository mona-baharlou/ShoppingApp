package com.bahrlou.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { ShoppingAppTheme { ShoppingUi() } }
    }
}

@Composable
fun ShoppingUi() {


    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "mainScreen"
    ) {

        composable("mainScreen") {
            MainScreen()
        }


        composable("productScreen") {
            ProductScreen()
        }

        composable("categoryScreen") {
            CategoryScreen()
        }

        composable("profileScreen") {
            ProfileScreen()
        }

        composable("cartScreen") {
            CartScreen()
        }

        composable("signUpScreen") {
            SignUpScreen()
        }

        composable("signInScreen") {
            SignInScreen()
        }

        composable("introScreen") {
            IntroScreen()
        }

        composable("noInternetScreen") {
            NoInternetScreen()
        }

    }
}

@Composable
fun NoInternetScreen() {

    
}

@Composable
fun IntroScreen() {


}

@Composable
fun SignInScreen() {


}

@Composable
fun SignUpScreen() {


}

@Composable
fun CartScreen() {


}

@Composable
fun ProfileScreen() {


}

@Composable
fun CategoryScreen() {

}

@Composable
fun ProductScreen() {


}

@Composable
fun MainScreen() {

}


@Preview(showBackground = true)
@Composable
fun ShoppingPreview() {
    ShoppingAppTheme { ShoppingUi() }
}