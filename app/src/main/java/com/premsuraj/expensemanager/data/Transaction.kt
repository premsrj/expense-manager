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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Transaction

        if (date != other.date) return false
        if (amount != other.amount) return false
        if (payee != other.payee) return false
        if (categoryId != other.categoryId) return false
        if (categoryName != other.categoryName) return false
        if (description != other.description) return false
        if (isIncome != other.isIncome) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + amount.hashCode()
        result = 31 * result + payee.hashCode()
        result = 31 * result + categoryId.hashCode()
        result = 31 * result + categoryName.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + isIncome.hashCode()
        return result
    }


}