package com.bahrlou.shoppingapp.ui.features.cart

import android.content.res.Configuration
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.model.repository.cart.CartRepository
import com.bahrlou.shoppingapp.ui.theme.PriceBackground
import com.bahrlou.shoppingapp.ui.theme.Shapes
import com.bahrlou.shoppingapp.util.coroutineExceptionHandler
import com.bahrlou.shoppingapp.util.setPriceFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    val isNumberChanging = mutableStateOf(Pair("", false))
    val cartList = mutableStateOf(listOf<Product>())
    val totalPrice = mutableIntStateOf(0)

    init {
        getCartInfo()
    }

    private fun getCartInfo() {
        viewModelScope.launch(coroutineExceptionHandler) {

            val data = cartRepository.getCartInfo()

            cartList.value = data.productList
            totalPrice.value = data.totalPrice

        }
    }

    fun addCartItem(productId: String) {

        viewModelScope.launch(coroutineExceptionHandler) {

            isNumberChanging.value = isNumberChanging.value.copy(productId, true)

            val isSuccess = cartRepository.addToCart(productId)
            if (isSuccess) {
                getCartInfo()

            }
            delay(300)

            isNumberChanging.value = isNumberChanging.value.copy(productId, false)

        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch(coroutineExceptionHandler) {

            isNumberChanging.value = isNumberChanging.value.copy(productId, true)

            val isSuccess = cartRepository.removeFromCart(productId)
            if (isSuccess) {
                getCartInfo()

            }
            delay(300)

            isNumberChanging.value = isNumberChanging.value.copy(productId, false)

        }
    }

    /************************** PURChASE ***************************/
    @Composable
    fun Purchase(
        totalPrice: String,
        OnPurchaseClicked: () -> Unit
    ) {

        val config = LocalConfiguration.current
        val fraction = if (config.orientation == Configuration.ORIENTATION_LANDSCAPE)
            0.15f else 0.07f


        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction), color = Color.White
        )
        {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(182.dp, 40.dp),
                    onClick = { OnPurchaseClicked.invoke() }) {

                    Text(
                        text = "Let's purchase",
                        modifier = Modifier.padding(2.dp),
                        color = Color.White,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    )

                }

                Surface(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(Shapes.large),
                    color = PriceBackground
                ) {

                    Text(
                        text = setPriceFormat(totalPrice),
                        modifier = Modifier.padding(
                            top = 6.dp,
                            bottom = 6.dp,
                            start = 8.dp,
                            end = 8.dp
                        ),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }

    }

}
}