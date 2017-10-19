package com.premsuraj.expensemanager.data

import java.util.*

class Transaction {
    var date = Date()
    var amount = 0.0f
    var payee = ""
    var categoryId = "0"
    var categoryName = "Other"
    var description = ""
    var isIncome = false
}