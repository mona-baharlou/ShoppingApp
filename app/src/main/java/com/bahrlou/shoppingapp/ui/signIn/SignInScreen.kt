package com.bahrlou.shoppingapp.ui.signIn

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bahrlou.shoppingapp.R
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.Blue
import com.bahrlou.shoppingapp.ui.theme.Shapes
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme
import com.bahrlou.shoppingapp.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel


@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    ShoppingAppTheme {
        Surface(
            color = BackgroundMain,
            modifier = Modifier.fillMaxSize()
        ) {
            SignInScreen()
            /*MainCardView() {

            }*/
        }
    }
}

@Composable
fun SignInScreen() {

    val navigation = getNavController()

    val viewModel = getViewModel<SignInViewModel>(
        //viewModelStoreOwner = navigation.getBackStackEntry("root")
    )

    Box {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(Blue)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppIcon()

            MainCardView(navigation, viewModel) {
                viewModel.userSignIn()
            }
        }
    }
}

@Composable
fun MainCardView(navigation: NavController, viewModel: SignInViewModel, signInEvent: () -> Unit) {

    val context = LocalContext.current

    val email = viewModel.email.observeAsState("")
    val password = viewModel.password.observeAsState("")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        elevation = 10.dp,
        shape = Shapes.medium
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(top = 18.dp, bottom = 18.dp),
                text = "Sign In",
                style = TextStyle(
                    color = Blue,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            MainTextField(
                edtValue = email.value,
                icon = R.drawable.ic_email,
                hint = "Email"
            ) {
                viewModel.email.value = it
            }

            PasswordTextField(
                edtValue = password.value,
                icon = R.drawable.ic_password,
                hint = "Password"
            ) {
                viewModel.password.value = it
            }


            Button(
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
                onClick = {
                    checkUserInput(email, password, signInEvent, context)
                }
            ) {
                Text(modifier = Modifier.padding(8.dp), text = "Log In")
            }


            Row(
                modifier = Modifier.padding(18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Text(text = "Don't have an account")

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(onClick = {
                    navigation.navigate(MyScreens.SignUpScreen.route) {
                        popUpTo(MyScreens.SignInScreen.route) {
                            inclusive = true
                        }
                    }
                }) {
                    Text(text = "Register now", color = Blue)
                }

            }

        }

    }
}

private fun checkUserInput(
    email: State<String>,
    password: State<String>,
    signInEvent: () -> Unit,
    context: Context
) {
    if (email.value.isNotEmpty() && password.value.isNotEmpty()) {

        if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            signInEvent.invoke()
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.email_format_is_not_valid),
                Toast.LENGTH_SHORT
            )
                .show()
        }

    } else {
        Toast.makeText(
            context,
            context.getString(R.string.please_fill_required_data_first), Toast.LENGTH_SHORT
        )
            .show()
    }
}

@Composable
fun AppIcon() {

    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .size(64.dp)
    ) {

        Image(
            modifier = Modifier.padding(14.dp),
            painter = painterResource(id = R.drawable.ic_icon_app),
            contentDescription = null
        )

    }
}

@Composable
fun MainTextField(
    edtValue: String,
    icon: Int,
    hint: String,
    onValueChanges: (String) -> Unit

) {
    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(
                top = 12.dp
            ),
        shape = Shapes.medium,
        leadingIcon = { Icon(painterResource(icon), null) },


        )
}


@Composable
fun PasswordTextField(
    edtValue: String,
    icon: Int,
    hint: String,
    onValueChanges: (String) -> Unit

) {

    val passwordVisibility = remember {
        mutableStateOf(false)
    }



    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(
                top = 12.dp
            ),
        shape = Shapes.medium,
        leadingIcon = { Icon(painterResource(icon), null) },
        visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisibility.value) painterResource(id = R.drawable.ic_invisible)
            else
                painterResource(id = R.drawable.ic_visible)

            Icon(painter = image, contentDescription = null, modifier = Modifier.clickable {
                passwordVisibility.value = !passwordVisibility.value
            })
        })
}






