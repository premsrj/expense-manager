package com.premsuraj.expensemanager.addedit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.google.firebase.crash.FirebaseCrash
import com.premsuraj.expensemanager.Constants
import com.premsuraj.expensemanager.MyApplication
import com.premsuraj.expensemanager.data.Transaction
import java.lang.Exception

class AddEditViewModel constructor(application: Application) : AndroidViewModel(application) {

    private var transactionData: Transaction = Transaction()

    fun loadTransaction(id: String?, onLoaded: (Transaction) -> Unit) {
        if (id == null || id.isBlank()) {
            onLoaded.invoke(transactionData)
            return
        }

        getApplication<MyApplication>().firebaseDb.collection(Constants.References.TRANSACTIONS)
                .document(id).get().addOnSuccessListener { documentSnapshot ->
            try {
                transactionData = documentSnapshot.toObject(Transaction::class.java)
            } catch (ex: Exception) {
                FirebaseCrash.report(ex)
                transactionData = Transaction()
            }
            onLoaded.invoke(transactionData)
        }.addOnFailureListener { exception ->
            FirebaseCrash.report(exception)
            onLoaded.invoke(transactionData)
        }
    }

    fun getTransaction(): Transaction {
        return transactionData
    }

}