package com.bahrlou.shoppingapp.ui.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bahrlou.shoppingapp.di.shoppingModules
import com.bahrlou.shoppingapp.ui.features.IntroScreen
import com.bahrlou.shoppingapp.ui.features.signIn.SignInScreen
import com.bahrlou.shoppingapp.ui.features.signUp.SignUpScreen
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme
import com.bahrlou.shoppingapp.util.KEY_CATEGORY_ARG
import com.bahrlou.shoppingapp.util.KEY_PRODUCT_ARG
import com.bahrlou.shoppingapp.util.MyScreens
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            Koin(appDeclaration = {

                androidContext(this@MainActivity)
                modules(shoppingModules)

            }) {

                ShoppingAppTheme {
                    Surface(
                        color = BackgroundMain, modifier = Modifier.fillMaxSize()
                    ) {
                        ShoppingUi()
                    }
                }

            }

        }
    }
}

@Composable
fun ShoppingUi() {


    val navController = rememberNavController()

    KoinNavHost(
        navController = navController,
        startDestination = MyScreens.IntroScreen.route
    ) {

        composable(MyScreens.MainScreen.route) {
            MainScreen()
        }


        composable(
            route = "${MyScreens.ProductScreen.route}" /*+
                    "/$KEY_PRODUCT_ARG",
            arguments = listOf(navArgument(KEY_PRODUCT_ARG) {
                type = NavType.IntType
            })*/
        ) {
            ProductScreen(it.arguments!!.getInt(KEY_PRODUCT_ARG, -1)) //productId
        }

        composable(
            route = "${MyScreens.CategoryScreen.route}" /*+
                    "/$KEY_CATEGORY_ARG",
            arguments = listOf(navArgument(KEY_CATEGORY_ARG) {
                type = NavType.StringType
            })*/
        ) {
            CategoryScreen(it.arguments!!.getString(KEY_CATEGORY_ARG, "null"))
        }

        composable(MyScreens.ProfileScreen.route) {
            ProfileScreen()
        }

        composable(MyScreens.CartScreen.route) {
            CartScreen()
        }

        composable(MyScreens.SignUpScreen.route) {
            SignUpScreen()
        }

        composable(MyScreens.SignInScreen.route) {
            SignInScreen()
        }

        composable(MyScreens.IntroScreen.route) {
            IntroScreen()
        }

        composable(MyScreens.NoInternetScreen.route) {
            NoInternetScreen()
        }

    }
}

@Composable
fun NoInternetScreen() {


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
fun DefaultPreview() {
    ShoppingAppTheme {
        Surface(
            color = BackgroundMain, modifier = Modifier.fillMaxSize()
        ) {
            ShoppingUi()
        }
    }
}