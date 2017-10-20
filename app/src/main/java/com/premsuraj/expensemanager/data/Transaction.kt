package com.premsuraj.expensemanager.data

import io.realm.RealmObject
import java.util.*

open class Transaction : RealmObject() {
    var date = Date()
    var amount = 0.0f
    var payee = ""
    var categoryId = "0"
    var categoryName = "Other"
    var description = ""
    var isIncome = false
}