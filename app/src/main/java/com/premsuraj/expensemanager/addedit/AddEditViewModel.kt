package com.premsuraj.expensemanager.addedit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.google.firebase.crash.FirebaseCrash
import com.premsuraj.expensemanager.data.Transaction
import io.realm.Realm
import java.lang.Exception
import java.util.*

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

    fun fetchPayees(onFetched: () -> Unit) {
//        getApplication<MyApplication>().firebaseDb.collection(Constants.DbReferences.TRANSACTIONS).
    }

    fun setCategory(categoryId: String, categoryName: String) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        transactionData.categoryId = categoryId
        transactionData.categoryName = categoryName
        realm.commitTransaction()
    }

    fun updateTransaction(date: Date, description: String, payee: String, amount: Float, isIncome: Boolean,
                          categoryId: String, categoryName: String, onSaved: () -> Unit,
                          onFailed: () -> Unit) {
        Realm.getDefaultInstance().executeTransaction { realm ->
            try {
                transactionData.date = date
                transactionData.description = description
                transactionData.payee = payee
                transactionData.amount = amount
                transactionData.isIncome = isIncome
                transactionData.categoryId = categoryId
                transactionData.categoryName = categoryName
                onSaved.invoke()
            } catch (ex: Exception) {
                FirebaseCrash.report(ex)
                onFailed.invoke()
            }
        }
    }
}