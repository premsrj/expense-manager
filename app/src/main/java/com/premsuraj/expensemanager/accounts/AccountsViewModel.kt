package com.premsuraj.expensemanager.accounts

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.premsuraj.expensemanager.data.Account
import io.realm.Realm

class AccountsViewModel(application: Application) : AndroidViewModel(application) {
    fun getAllAccounts(): List<Account> {
        return Realm.getDefaultInstance().where(Account::class.java).findAll()
    }

    fun save(account: Account, name: String, balance: Float, onSaved: () -> Unit) {
        val db = Realm.getDefaultInstance()
        db.beginTransaction()
        account.name = name
        account.initialBalance = balance
        if (account.id.isBlank())
            account.id = "" + account.hashCode()
        db.insertOrUpdate(account)
        db.commitTransaction()
        onSaved.invoke()
    }
}