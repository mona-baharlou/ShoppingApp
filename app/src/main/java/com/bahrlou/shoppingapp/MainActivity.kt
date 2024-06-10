package com.bahrlou.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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


        composable(
            route = "productScreen/{productId}",
            arguments = listOf(navArgument("productId") {
                type = NavType.IntType
            })
        ) {
            ProductScreen(it.arguments!!.getInt("productId", -1)) //productId
        }

        composable(route = "categoryScreen/{categoryName}", arguments = listOf(navArgument("categoryName") {
            type = NavType.StringType
        })) {
            CategoryScreen(it.arguments!!.getString("categoryName", "null"))
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
fun CategoryScreen(categoryName: String) {

}

@Composable
fun ProductScreen(productId: Int) {


}

@Composable
fun MainScreen() {

}


@Preview(showBackground = true)
@Composable
fun ShoppingPreview() {
    ShoppingAppTheme { ShoppingUi() }
}