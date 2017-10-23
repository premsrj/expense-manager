package com.premsuraj.expensemanager.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Account : RealmObject() {
    @PrimaryKey
    @Required
    var id = ""
    var name = ""
    var initialBalance = 0.0f

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (name != other.name) return false
        if (initialBalance != other.initialBalance) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + initialBalance.hashCode()
        return result
    }


}