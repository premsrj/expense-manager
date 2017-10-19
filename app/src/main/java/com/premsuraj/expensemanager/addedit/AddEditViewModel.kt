package com.premsuraj.expensemanager.addedit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.google.firebase.crash.FirebaseCrash
import com.google.firebase.firestore.DocumentReference
import com.premsuraj.expensemanager.Constants
import com.premsuraj.expensemanager.MyApplication
import com.premsuraj.expensemanager.data.Transaction
import java.lang.Exception

class AddEditViewModel constructor(application: Application) : AndroidViewModel(application) {

    private var transactionData: Transaction = Transaction()
    private var documentRef: DocumentReference? = null

    fun loadTransaction(id: String?, onLoaded: (Transaction) -> Unit) {
        if (id == null || id.isBlank()) {
            onLoaded.invoke(transactionData)
            return
        }

        documentRef = getApplication<MyApplication>().firebaseDb.collection(Constants.DbReferences.TRANSACTIONS)
                .document(id)
        if (documentRef == null) {
            onLoaded.invoke(transactionData)
            return
        }

        documentRef?.get()?.addOnSuccessListener { documentSnapshot ->
            try {
                transactionData = documentSnapshot.toObject(Transaction::class.java)
            } catch (ex: Exception) {
                FirebaseCrash.report(ex)
                transactionData = Transaction()
            }
            onLoaded.invoke(transactionData)
        }?.addOnFailureListener { exception ->
            FirebaseCrash.report(exception)
            onLoaded.invoke(transactionData)
        }
    }

    fun getTransaction(): Transaction {
        return transactionData
    }

    fun saveTransaction(onSaved: () -> Unit, onFailed: () -> Unit) {
        if (documentRef == null) {
            documentRef = getApplication<MyApplication>().firebaseDb.collection(Constants.DbReferences.TRANSACTIONS).document()
        }

        documentRef?.set(transactionData)?.addOnSuccessListener { void ->
            onSaved.invoke()
        }?.addOnFailureListener { exception ->
            FirebaseCrash.report(exception)
            onFailed.invoke()
        }
    }

}