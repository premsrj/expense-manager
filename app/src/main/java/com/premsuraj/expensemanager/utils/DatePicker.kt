package com.premsuraj.expensemanager.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import java.util.*


/**
 * Created by premsuraj
 */
class DatePicker constructor() : DialogFragment(), DatePickerDialog.OnDateSetListener {
    interface DatePickerListener {
        fun onDatePicked(year: Int, month: Int, day: Int)
    }

    private lateinit var datePickerListener: (year: Int, month: Int, day: Int) -> Unit
    private lateinit var date: Date

    @SuppressLint("ValidFragment")
    constructor(date: Date, datePickerListener: (year: Int, month: Int, day: Int) -> Unit) : this() {
        this.date = date
        this.datePickerListener = datePickerListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        c.time = date
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: android.widget.DatePicker?, year: Int, month: Int, day: Int) {
        datePickerListener.invoke(year, month, day)
    }
}