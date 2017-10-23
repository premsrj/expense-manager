package com.example.premsuraj.expensemanager.utils

import android.text.Editable
import android.view.View
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Date.toFormattedString(): String {
    val format = SimpleDateFormat("dd-MMM-yyyy hh:mm a")
    return format.format(this)
}

fun CharSequence.toDate(): Date {
    val format = SimpleDateFormat("dd-MMM-yyyy hh:mm a")
    return format.parse(this.toString())
}

fun Float.toFormattedString(): String {
    return String.format("%.2f", this)
}

fun Float.isBlank(): Boolean {
    return this == 0.0f
}

fun Editable.isValidNumber(): Boolean {
    try {
        val number = this.toString().toFloat()
        if (number > 0.0f) return true
        return false
    } catch (ex: Exception) {
        return false
    }
}