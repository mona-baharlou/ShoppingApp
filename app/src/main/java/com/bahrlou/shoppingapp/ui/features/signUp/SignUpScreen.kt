package com.bahrlou.shoppingapp.ui.features.signUp

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
import androidx.compose.runtime.SideEffect
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
import com.bahrlou.shoppingapp.util.InternetChecker
import com.bahrlou.shoppingapp.util.MyScreens
import com.bahrlou.shoppingapp.util.SUCCESS
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    ShoppingAppTheme {
        Surface(
            color = BackgroundMain,
            modifier = Modifier.fillMaxSize()
        ) {
            SignUpScreen()
            /*MainCardView() {

            }*/
        }
    }
}

@Composable
private fun ChangeStatusBarColor() {
    val uiController = rememberSystemUiController()
    SideEffect {
        uiController.setStatusBarColor(Blue)
    }
}

@Composable
fun SignUpScreen() {

    ChangeStatusBarColor()

    val context = LocalContext.current

    val navigation = getNavController()

    val viewModel = getViewModel<SignUpViewModel>(
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
                .fillMaxHeight(0.95f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppIcon()

            MainCardView(navigation, viewModel) {
                viewModel.userSignUp {
                    if (it == SUCCESS) {
                        navigation.navigate(MyScreens.MainScreen.route)
                        {
                            popUpTo(MyScreens.IntroScreen.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun MainCardView(navigation: NavController, viewModel: SignUpViewModel, signUpEvent: () -> Unit) {

    val context = LocalContext.current

    val name = viewModel.name.observeAsState("") //remember { mutableStateOf("") }
    val email = viewModel.email.observeAsState("")
    val password = viewModel.password.observeAsState("")
    val confirmPassword = viewModel.confirmPassword.observeAsState("")

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
                text = "Sign Up",
                style = TextStyle(
                    color = Blue,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            )


            MainTextField(
                edtValue = name.value,
                icon = R.drawable.ic_person,
                hint = "Your Full Name"
            ) {
                viewModel.name.value = it
            }


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



            PasswordTextField(
                edtValue = confirmPassword.value,
                icon = R.drawable.ic_password,
                hint = "Confirm Password"
            ) {
                viewModel.confirmPassword.value = it
            }


            Button(
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
                onClick = {
                    checkUserInput(name, email, password, confirmPassword, signUpEvent, context)
                }
            ) {
                Text(modifier = Modifier.padding(8.dp), text = "Register Account")
            }


            Row(
                modifier = Modifier.padding(18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Text(text = "Already have an account")

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(onClick = {
                    navigation.navigate(MyScreens.SignInScreen.route) {
                        popUpTo(MyScreens.SignUpScreen.route) {
                            inclusive = true
                        }
                    }
                }) {
                    Text(text = "Log In", color = Blue)
                }

            }

        }

    }
}

private fun checkUserInput(
    name: State<String>,
    email: State<String>,
    password: State<String>,
    confirmPassword: State<String>,
    signUpEvent: () -> Unit,
    context: Context
) {
    if (name.value.isNotEmpty() && email.value.isNotEmpty() && password.value.isNotEmpty() && confirmPassword.value.isNotEmpty()) {

        if (password.value == confirmPassword.value) {

            if (password.value.length >= 8) {
                if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {

                    if (InternetChecker(context).isInternetConnected) {
                        signUpEvent.invoke()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.please_check_your_network_connectivity),
                            Toast.LENGTH_SHORT
                        )
                            .show()

                    }
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
                    context.getString(R.string.password_s_length_should_be_more_than_8_characters),
                    Toast.LENGTH_SHORT
                )
                    .show()

            }

        } else {
            Toast.makeText(
                context,
                context.getString(R.string.passwords_are_not_the_same), Toast.LENGTH_SHORT
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






