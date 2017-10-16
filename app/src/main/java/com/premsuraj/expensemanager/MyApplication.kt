package com.premsuraj.expensemanager

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MyApplication : Application() {
    lateinit var firebaseDb: FirebaseFirestore


    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        firebaseDb = FirebaseFirestore.getInstance()
    }
}