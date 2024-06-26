package com.bahrlou.shoppingapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/*private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)*/

private val LightColorScheme = lightColors(
    primary = Blue,
    secondary = Blue,
    background = BackgroundMain,
    //surface = CardBackground,

    //secondary = PurpleGrey40,
    //tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ShoppingAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    /* val colorScheme = when {
         dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
             val context = LocalContext.current
             //if (darkTheme) dynamicDarkColorScheme(context)
             // else
             dynamicLightColorScheme(context)
         }

         darkTheme -> LightColorScheme
         else -> LightColorScheme
     }
 */
    val colors = LightColorScheme

    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content,
        shapes = Shapes
    )

    val systemUiController = rememberSystemUiController()
    //for statusBar color
    systemUiController.setSystemBarsColor(Blue)

}