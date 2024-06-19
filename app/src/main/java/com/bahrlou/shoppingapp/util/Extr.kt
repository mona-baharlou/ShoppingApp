package com.bahrlou.shoppingapp.util

import android.annotation.SuppressLint
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import java.text.SimpleDateFormat
import java.util.Calendar

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.v("Error", "Error: ${throwable.message}")
}


@SuppressLint("SimpleDateFormat")
fun setTimeFormat(timeInMillies: Long): String {

    val formatter = SimpleDateFormat("yyyy/MM/dd hh:mm")

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillies

    return formatter.format(calendar.time)
}


fun setPriceFormat(oldPrice: String): String {

    if (oldPrice.length > 3) {

        val reversed = oldPrice.reversed()
        var newPrice = ""

        for (i in oldPrice.indices) {
            newPrice += reversed[i]

            if ((i + 1) % 3 == 0) {
                newPrice += ','
            }

        }
        val readyToGo = newPrice.reversed()

        if (readyToGo.first() == ',') {
            return "$" + readyToGo.substring(1)
        }

        return "$" + readyToGo
    }

    return  "$" +oldPrice
}

