package com.bahrlou.shoppingapp.ui.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bahrlou.shoppingapp.R
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.Blue
import com.bahrlou.shoppingapp.ui.theme.Shapes
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme

@Composable
fun SignUpScreen() {
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

            MainCardView {

            }
        }
    }
}

@Composable
fun MainCardView(SignUpEvent: () -> Unit) {

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
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
                name.value = it
            }


            MainTextField(
                edtValue = email.value,
                icon = R.drawable.ic_email,
                hint = "Email"
            ) {
                email.value = it
            }



            MainTextField(
                edtValue = password.value,
                icon = R.drawable.ic_password,
                hint = "Password"
            ) {
                password.value = it
            }



            MainTextField(
                edtValue = confirmPassword.value,
                icon = R.drawable.ic_password,
                hint = "Confirm Password"
            ) {
                confirmPassword.value = it
            }


            Button(
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
                onClick = SignUpEvent
            ) {
                Text(modifier = Modifier.padding(8.dp), text = "Register Account")
            }


            Row(
                modifier = Modifier.padding(18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Text(text = "Already have an acoount")

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(onClick = {}) {
                    Text(text = "Log In", color = Blue)
                }

            }

        }

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