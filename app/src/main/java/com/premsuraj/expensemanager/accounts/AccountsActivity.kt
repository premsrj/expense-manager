package com.premsuraj.expensemanager.accounts

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.premsuraj.expensemanager.R
import com.premsuraj.expensemanager.data.Account
import kotlinx.android.synthetic.main.activity_accounts.*
import kotlinx.android.synthetic.main.content_accounts.*
import org.jetbrains.anko.toast

class AccountsActivity : AppCompatActivity() {
    lateinit var viewModel: AccountsViewModel
    lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts)
        setSupportActionBar(toolbar)

        accountsList.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        accountsList.layoutManager = layoutManager

        viewModel = ViewModelProviders.of(this).get(AccountsViewModel::class.java)

        fab.setOnClickListener { view ->
            addNewAccount()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun addNewAccount() {
        showAccountDialog(Account())
    }

    private fun showAccountDialog(account: Account) {
        AccountDetailsDialog(this, account, { name, balance ->
            if (name.isBlank()) {
                toast("Name is required")
                return@AccountDetailsDialog
            }
            viewModel.save(account, name, balance, {
                refreshList()
            })
        }).show()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        val accounts = viewModel.getAllAccounts()
        accountsList.adapter = AccountAdapter(accounts, { account ->
            showAccountDialog(account)
        })
    }

    class AccountViewHolder(val view: TextView) : RecyclerView.ViewHolder(view) {
        var account: Account = Account()
    }

    class AccountAdapter(val entries: List<Account>, val onAccountClicked: (Account) -> Unit) : RecyclerView.Adapter<AccountViewHolder>() {
        override fun getItemCount(): Int {
            return entries.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AccountViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.account_item, parent, false)

            val viewHolder = AccountViewHolder(view as TextView)
            viewHolder.view.setOnClickListener {
                onAccountClicked(viewHolder.account)
            }
            return viewHolder
        }

        override fun onBindViewHolder(holder: AccountViewHolder?, position: Int) {
            holder?.view?.text = entries[position].name
            holder?.account = entries[position]
        }
    }
}
