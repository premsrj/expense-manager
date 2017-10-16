package com.premsuraj.expensemanager.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DialogFragment
import android.app.TimePickerDialog
import android.os.Bundle
import java.util.*


class TimePicker constructor() : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    interface TimePickerListener {
        fun onTimePicked(hourOfDay: Int, minute: Int)
    }

    private lateinit var timePickerListener: (hourOfDay: Int, minute: Int) -> Unit
    private lateinit var date: Date

    @SuppressLint("ValidFragment")
    constructor(date: Date, timePickerListener: (hourOfDay: Int, minute: Int) -> Unit) : this() {
        this.date = date
        this.timePickerListener = timePickerListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        c.time = date
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, false)
    }

    override fun onTimeSet(view: android.widget.TimePicker?, hourOfDay: Int, minute: Int) {
        timePickerListener.invoke(hourOfDay, minute)
    }
}