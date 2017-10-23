package com.premsuraj.expensemanager.accounts

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.premsuraj.expensemanager.utils.isBlank
import com.example.premsuraj.expensemanager.utils.toFormattedString
import com.premsuraj.expensemanager.R
import com.premsuraj.expensemanager.data.Account
import kotlinx.android.synthetic.main.account_details_dialog.*

class AccountDetailsDialog(context: Context, private val account: Account,
                           private val onSave: (name: String, balance: Float) -> Unit) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_details_dialog)
        setTitle("Account Details")
        if (!account.name.isBlank()) name.setText(account.name)
        if (!account.initialBalance.isBlank()) initialBalance.setText(account.initialBalance.toFormattedString())

        save.setOnClickListener {
            onSave.invoke(name.text.toString(), initialBalance.text.toString().toFloat())
            this@AccountDetailsDialog.dismiss()
        }
    }
}