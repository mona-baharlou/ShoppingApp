package com.bahrlou.shoppingapp.util

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.v("Error", "Error: ${throwable.message}")
}