package com.premsuraj.expensemanager.addedit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.util.Log
import com.google.firebase.crash.FirebaseCrash
import com.premsuraj.expensemanager.data.Transaction
import io.realm.Realm
import java.lang.Exception
import java.util.*
import java.util.stream.Collectors

class AddEditViewModel constructor(application: Application) : AndroidViewModel(application) {

    private var transactionData: Transaction = Transaction()

    fun loadTransaction(id: String?, onLoaded: (Transaction) -> Unit) {
        if (id == null || id.isBlank()) {
            onLoaded.invoke(transactionData)
            return
        }

        try {
            val realm = Realm.getDefaultInstance()
            transactionData = realm.where(Transaction::class.java)
                    .equalTo("id", id).findFirst()!!
            onLoaded.invoke(transactionData)
        } catch (ex: Exception) {
            transactionData = Transaction()
            onLoaded.invoke(transactionData)
        }
    }

    fun getTransaction(): Transaction {
        return transactionData
    }

    fun saveTransaction(onSaved: () -> Unit, onFailed: () -> Unit) {
        try {
            val realm = Realm.getDefaultInstance()
            if (transactionData.id.isBlank())
                transactionData.id = "" + transactionData.hashCode()
            realm.executeTransaction { realm ->
                realm.insertOrUpdate(transactionData)
                onSaved.invoke()
            }
        } catch (ex: Exception) {
            FirebaseCrash.report(ex)
            onFailed.invoke()
        }
    }

    fun fetchPayees(onFetched: (List<String>) -> Unit) {
        val transactions = Realm.getDefaultInstance().where(Transaction::class.java)
                .distinct("payee")
        val payees = transactions.stream().map(Transaction::payee).collect(Collectors.toList())
        Log.v("Test", payees.toString())
        onFetched.invoke(payees)
    }

    fun setCategory(categoryId: String, categoryName: String) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        transactionData.categoryId = categoryId
        transactionData.categoryName = categoryName
        realm.commitTransaction()
    }

    fun updateTransaction(date: Date, description: String, payee: String, amount: Float, isIncome: Boolean,
                          categoryId: String, categoryName: String): Boolean {
        try {
            Realm.getDefaultInstance().beginTransaction()
            transactionData.date = date
            transactionData.description = description
            transactionData.payee = payee
            transactionData.amount = amount
            transactionData.isIncome = isIncome
            transactionData.categoryId = categoryId
            transactionData.categoryName = categoryName
            Realm.getDefaultInstance().commitTransaction()
            return true
        } catch (ex: Exception) {
            FirebaseCrash.report(ex)
            return false
        }
    }
}