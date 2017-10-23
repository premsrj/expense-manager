package com.premsuraj.expensemanager.addedit

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.premsuraj.expensemanager.utils.*
import com.premsuraj.expensemanager.Constants
import com.premsuraj.expensemanager.R
import com.premsuraj.expensemanager.data.Transaction
import com.premsuraj.expensemanager.utils.DatePicker
import com.premsuraj.expensemanager.utils.TimePicker
import kotlinx.android.synthetic.main.activity_add_edit.*
import java.util.*

class AddEditActivity : AppCompatActivity() {
    lateinit var viewModel: AddEditViewModel
    var categoryId = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        date.text = Date().toFormattedString()

        viewModel = ViewModelProviders.of(this).get(AddEditViewModel::class.java)

        val transactionId = intent?.getStringExtra(Constants.KEYS.TRANSACTION)

        viewModel.loadTransaction(transactionId, { transaction ->

            refreshViewData(transaction)

            date.setOnClickListener { _ -> updateDate() }
            category.setOnClickListener { _ ->
                val intent = Intent(this@AddEditActivity, CategoryPicker::class.java)
                startActivityForResult(intent, 101)
            }

            progressBar.invisible()
        })

        isincome.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) compoundButton.text = "Income" else compoundButton.text = "Expense"
        }

        amount.addTextChangedListener(CustomTextWatcher())
        viewModel.fetchPayees { payees ->
            payee.threshold = 1
            payee.setAdapter(ArrayAdapter<String>(this, android.R.layout.select_dialog_item,
                    payees))
        }
    }

    private fun refreshViewData(transaction: Transaction) {
        date.text = transaction.date.toFormattedString()
        amount.setText(if (transaction.amount.isEmpty()) "" else transaction.amount.toString())
        payee.setText(transaction.payee)
        description.setText(transaction.description)
        isincome.isChecked = transaction.isIncome
        category.text = transaction.categoryName
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_addedit, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_save)?.isVisible =
                (amount.text.toString().isEmpty() || amount.text.toString().toFloat().isEmpty())
                        .not()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_save -> saveEntry()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveEntry(): Boolean {
        saveTransaction()
        return true
    }

    private fun saveTransaction() {
        progressBar.visible()
        val result = viewModel.updateTransaction(date.text.toDate(), description.text.toString(),
                payee.text.toString(),
                if (amount.text.isNotBlank()) amount.text.toString().toFloat() else 0f,
                isincome.isChecked, categoryId, category.text.toString())
        if (result) {
            viewModel.saveTransaction({
                Toast.makeText(this@AddEditActivity, "Saved", Toast.LENGTH_SHORT).show()
                this@AddEditActivity.finish()
                progressBar.invisible()
            }, {
                Toast.makeText(this@AddEditActivity, "Cannot save", Toast.LENGTH_LONG).show()
                progressBar.invisible()
            })
        } else {
            Toast.makeText(this@AddEditActivity, "Cannot save", Toast.LENGTH_LONG).show()
            progressBar.invisible()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null)
            return
        categoryId = data.getStringExtra(Constants.KEYS.CATEGORY_ID)
        category.text = data.getStringExtra(Constants.KEYS.CATEGORY_NAME)
    }

    internal inner class CustomTextWatcher : TextWatcher {

        override fun afterTextChanged(p0: Editable?) {
            this@AddEditActivity.invalidateOptionsMenu()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }
}
