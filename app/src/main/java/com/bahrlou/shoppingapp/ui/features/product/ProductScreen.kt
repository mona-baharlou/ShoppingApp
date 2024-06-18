package com.bahrlou.shoppingapp.ui.features.product

import androidx.compose.ui.window.Dialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bahrlou.shoppingapp.R
import com.bahrlou.shoppingapp.model.data.Comment
import com.bahrlou.shoppingapp.model.data.Product
import com.bahrlou.shoppingapp.ui.theme.BackgroundMain
import com.bahrlou.shoppingapp.ui.theme.Blue
import com.bahrlou.shoppingapp.ui.theme.Shapes
import com.bahrlou.shoppingapp.ui.theme.ShoppingAppTheme
import com.bahrlou.shoppingapp.util.InternetChecker
import com.bahrlou.shoppingapp.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel


@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    ShoppingAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = BackgroundMain
        ) {
            //ProductScreen("")
        }
    }
}

@Composable
fun ProductScreen(productId: String) {

    val context = LocalContext.current
    val navigation = getNavController()
    val viewModel = getViewModel<ProductViewModel>()
    viewModel.loadData(productId, InternetChecker(context).isInternetConnected)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 58.dp),

            ) {

            ProductToolbar(
                name = "Details",
                badgeNumber = 4,
                onBackClicked = {
                    navigation.popBackStack()
                },
                onCartClicked = {
                    if (InternetChecker(context).isInternetConnected) {
                        navigation.navigate(MyScreens.CartScreen.route)
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.please_check_your_network_connectivity),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )

            ProductItem(data = viewModel.product.value,
                comments = viewModel.comments.value,
                OnCategoryClicked = {
                    navigation.navigate(MyScreens.CategoryScreen.route + "/${it}")
                })

        }


        AddToCart()

    }

}

@Composable
fun ProductItem(data: Product, comments: List<Comment>, OnCategoryClicked: (String) -> Unit) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        ProductInfoSection(data, OnCategoryClicked)

        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 14.dp, bottom = 14.dp)
        )

        ProductDetail(data, comments.size)

        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 14.dp)
        )


        CommentSection(comments) {

        }


    }
}


@Composable
fun ProductDetail(data: Product, commentNumber: Int) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Image(
                    painterResource(id = R.drawable.ic_details_comment),
                    contentDescription = null,
                    modifier = Modifier.size(
                        26.dp
                    )
                )

                Text(
                    text = "$commentNumber Comments",
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 13.sp
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 10.dp)
            ) {

                Image(
                    painterResource(id = R.drawable.ic_details_material),
                    contentDescription = null,
                    modifier = Modifier.size(
                        26.dp
                    )
                )

                Text(
                    text = data.material,
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 13.sp
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 10.dp)
            ) {

                Image(
                    painterResource(id = R.drawable.ic_details_sold),
                    contentDescription = null,
                    modifier = Modifier.size(
                        26.dp
                    )
                )

                Text(
                    text = "${data.soldItem} Sold",
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 13.sp
                )

            }

        }

        Surface(
            modifier = Modifier
                .clip(Shapes.large)
                .align(Alignment.Bottom),
            color = Blue
        ) {
            Text(
                text = data.tags, color = Color.White,
                modifier = Modifier.padding(6.dp),
                style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium)
            )

        }

    }

}

@Composable
fun ProductInfoSection(product: Product, OnCategoryClicked: (String) -> Unit) {

    AsyncImage(
        model = product.imgUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(Shapes.medium)
    )


    Text(
        text = product.name,
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(top = 14.dp)
    )


    Text(
        text = product.detailText,
        style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Justify),
        modifier = Modifier.padding(top = 4.dp),
    )


    TextButton(
        onClick = { OnCategoryClicked.invoke(product.category) },
        modifier = Modifier.padding(top = 4.dp),
    ) {
        Text(
            text = "#" + product.category,
            style = TextStyle(fontSize = 13.sp),
        )
    }
}

@Composable
fun ProductToolbar(
    name: String,
    badgeNumber: Int,
    onBackClicked: () -> Unit,
    onCartClicked: () -> Unit
) {
    TopAppBar(
        elevation = 2.dp,
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                text = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = 24.dp
                    ),
                textAlign = TextAlign.Center
            )
        },

        actions = {
            IconButton(
                onClick = { onCartClicked.invoke() },
                modifier = Modifier.padding(end = 6.dp)
            ) {
                if (badgeNumber == 0) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null)
                } else {
                    BadgedBox(badge = {
                        Badge {
                            Text(text = badgeNumber.toString())
                        }
                    }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    }
                }
            }
        },

        navigationIcon = {

            IconButton(onClick = { onBackClicked.invoke() }) {

                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null
                )

            }
        }


    )
}

@Composable
fun AddToCart() {

}

//****************************** Comments ********************************//

@Composable
fun CommentSection(comments: List<Comment>, AddNewComment: (String) -> Unit) {

    val showCommentDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (comments.isNotEmpty()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.comments),
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )

            AddComment(context, showCommentDialog)

            comments.forEach {
                CommentBody(comment = it)
            }


        }
    } else {
        AddComment(context, showCommentDialog)
    }


    if (showCommentDialog.value) {
        NewCommentDialog(OnDismiss = {
            showCommentDialog.value = false
        }, OnPositiveClicked = {
            AddNewComment.invoke(it)
        })
    }
}

@Composable
fun NewCommentDialog(
    OnDismiss: () -> Unit,
    OnPositiveClicked: (String) -> Unit
) {

    val context = LocalContext.current
    val userComment = remember {
        mutableStateOf("")
    }

    Dialog(onDismissRequest = OnDismiss) {
        Card(
            modifier = Modifier.fillMaxHeight(0.49f),
            elevation = 8.dp,
            shape = Shapes.medium
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Write your comment",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                //Enter data
                MainTextField(
                    edtValue = userComment.value,
                    hint = "Write your comment..."
                ) {
                    userComment.value = it
                }

                //Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(onClick = { OnDismiss.invoke() }) {
                        Text(text = "Cancel")
                    }

                    Spacer(modifier = Modifier.width(4.dp))


                    TextButton(onClick = {
                        if (userComment.value.isNotEmpty() && userComment.value.isNotBlank()) {
                            if (InternetChecker(context).isInternetConnected) {
                                OnPositiveClicked.invoke(userComment.value)
                                OnDismiss.invoke()
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.please_check_your_network_connectivity),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                OnDismiss.invoke()

                            }
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.please_write_your_comment),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            OnDismiss.invoke()

                        }

                    }) {
                        Text(text = "Ok")
                    }
                }
            }
        }
    }


}

@Composable
private fun AddComment(
    context: Context,
    showCommentDialog: MutableState<Boolean>
) {
    TextButton(onClick = {
        if (InternetChecker(context).isInternetConnected) {
            showCommentDialog.value = true
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.please_check_your_network_connectivity),
                Toast.LENGTH_SHORT
            ).show()
        }

    }) {
        Text(
            text = "Add New Comment",
            style = TextStyle(fontSize = 14.sp)
        )

    }
}


@Composable
fun CommentBody(comment: Comment) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        elevation = 0.dp,
        border = BorderStroke(1.dp, Color.LightGray),
        shape = Shapes.large
    ) {


        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = comment.userEmail,
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
            )


            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = comment.text,
                style = TextStyle(fontSize = 14.sp)
            )
        }
    }
}


@Composable
fun MainTextField(
    edtValue: String,
    hint: String,
    onValueChanges: (String) -> Unit

) {
    OutlinedTextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = { Text(hint) },
        value = edtValue,
        singleLine = false,
        maxLines = 2,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(
                top = 12.dp
            ),
        shape = Shapes.medium
    )
}


