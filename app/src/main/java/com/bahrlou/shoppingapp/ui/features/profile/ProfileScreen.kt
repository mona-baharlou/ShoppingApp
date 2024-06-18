package com.bahrlou.shoppingapp.ui.features.profile

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bahrlou.shoppingapp.R
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.Blue
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme
import com.bahrlou.shoppingapp.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ShoppingAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = BackgroundMain
        ) {
            ProfileScreen()
        }
    }
}

@Composable
fun ProfileScreen() {

    val context = LocalContext.current
    val navigation = getNavController()

    val viewModel = getViewModel<ProfileViewModel>()
    viewModel.getUserInfo()


    Box(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfileToolbar() { navigation.popBackStack() }

            MainAnimation()

            Spacer(modifier = Modifier.padding(top = 6.dp))

            ShowInfoSection("Email Address", viewModel.email.value, null)
            ShowInfoSection("Login Time", viewModel.loginTime.value, null)
            ShowInfoSection("Address", viewModel.address.value) {
                viewModel.showDialog.value = true
            }
            ShowInfoSection("Postal Code Address", viewModel.postalCode.value) {
                viewModel.showDialog.value = true

            }

            Button(
                onClick = {
                    Toast.makeText(context, "See you again !", Toast.LENGTH_SHORT).show()
                    viewModel.signOut()

                    navigation.navigate(MyScreens.MainScreen.route) {
                        popUpTo(MyScreens.MainScreen.route) {
                            inclusive = true
                        }

                        navigation.popBackStack()
                        navigation.popBackStack()
                    }

                }, modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 36.dp)
            ) {
                Text(text = "Sign Out")
            }
        }

    }


}

@Composable
fun ProfileToolbar(OnBackClicked: () -> Unit) {
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
                text = "Profile",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 58.dp)
            )
        }
    )


}

@Composable
fun ShowInfoSection(
    subject: String,
    text: String,
    OnLocationClicked: (() -> Unit)?
) {

    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .clickable { OnLocationClicked?.invoke() },
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = subject,
            style = TextStyle(
                fontSize = 18.sp,
                color = Blue,
                fontWeight = FontWeight.Bold
            )
        )


        Text(
            text = text,
            modifier = Modifier.padding(top = 2.dp),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        )

        Divider(
            color = Blue, thickness = 0.8.dp,
            modifier = Modifier.padding(top = 16.dp)
        )

    }

}

@Composable
fun MainAnimation() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.profile_anim)
    )

    LottieAnimation(
        modifier = Modifier
            .size(270.dp)
            .padding(start = 36.dp, bottom = 16.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}


