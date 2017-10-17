package com.premsuraj.expensemanager.addedit

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.premsuraj.expensemanager.utils.invisible
import com.example.premsuraj.expensemanager.utils.isEmpty
import com.example.premsuraj.expensemanager.utils.toDate
import com.example.premsuraj.expensemanager.utils.toFormattedString
import com.premsuraj.expensemanager.Constants
import com.premsuraj.expensemanager.R
import com.premsuraj.expensemanager.utils.DatePicker
import com.premsuraj.expensemanager.utils.TimePicker
import kotlinx.android.synthetic.main.activity_add_edit.*
import java.util.*

class AddEditActivity : AppCompatActivity() {
    lateinit var viewModel: AddEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        date.text = Date().toFormattedString()

        viewModel = ViewModelProviders.of(this).get(AddEditViewModel::class.java)

        var transactionId = intent?.getStringExtra(Constants.Ids.TRANSACTION)

        viewModel.loadTransaction(transactionId, { transaction ->

            date.text = transaction.date.toFormattedString()
            amount.setText(if (transaction.amount.isEmpty()) "" else transaction.amount.toString())
            payee.setText(transaction.payee)
            description.setText(transaction.description)

            date.setOnClickListener { _ -> updateDate() }

            progressBar.invisible()
        })
    }

    private fun updateDate() {
        val datePicker = DatePicker(date.text.toDate(), datePickerListener = { year: Int, month: Int, day: Int ->
            val timePicker = TimePicker(date.text.toDate(), timePickerListener = { hourOfDay, minute ->
                val cal = Calendar.getInstance()
                cal.set(year, month, day, hourOfDay, minute)
                date.text = cal.time.toFormattedString()
            })
            timePicker.show(fragmentManager, "timePicker")
        })
        datePicker.show(fragmentManager, "datePicker")
    }
}
