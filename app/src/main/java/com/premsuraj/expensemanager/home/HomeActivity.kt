package com.premsuraj.expensemanager.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.premsuraj.expensemanager.R
import com.premsuraj.expensemanager.accounts.AccountsActivity
import com.premsuraj.expensemanager.addedit.AddEditActivity
import com.premsuraj.expensemanager.base.BaseActivity
import com.premsuraj.expensemanager.data.Account
import io.realm.Realm
import kotlinx.android.synthetic.main.app_bar_home.*

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        fab.setOnClickListener { _ ->
            val intent = Intent(this, AddEditActivity::class.java)
//            intent.putExtra(Constants.KEYS.TRANSACTION, "1126777296")
            startActivity(intent)
        }
        super.initBaseViews()
    }

    override fun onStart() {
        super.onStart()
        if (getAllAccounts().isEmpty()) {
            val intent = Intent(this, AccountsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    fun getAllAccounts(): List<Account> {
        return Realm.getDefaultInstance().where(Account::class.java).findAll()
    }
}