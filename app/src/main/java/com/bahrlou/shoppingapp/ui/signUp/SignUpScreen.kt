package com.bahrlou.shoppingapp.ui.signUp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bahrlou.shoppingapp.R
import com.bahrlou.shoppingapp.ui.features.IntroScreen
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.Shapes
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme

@Composable
fun SignUpScreen() {
    MainTextField(
        edtValue = "salam",
        icon = R.drawable.ic_icon_app, hint = "Enter your name"
    ) {

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
            .fillMaxWidth(0.9f)
            .padding(
                top = 12.dp
            ),
        shape = Shapes.medium,
        leadingIcon = { Icon(painterResource(icon), null) },


        )
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    ShoppingAppTheme {
        Surface(
            color = BackgroundMain
        ) {
            SignUpScreen()
        }
    }
}