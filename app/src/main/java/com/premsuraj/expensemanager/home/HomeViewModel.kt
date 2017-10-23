package com.premsuraj.expensemanager.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.premsuraj.expensemanager.data.Transaction
import com.premsuraj.expensemanager.utils.*
import io.realm.Realm
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    fun getTotalIncome(): Float {
        val amount = Realm.getDefaultInstance().where(Transaction::class.java)
                .equalTo("isIncome", true)
                .sum("amount")
        return amount.toFloat()
    }

    fun getTotalExpense(): Float {
        val amount = Realm.getDefaultInstance().where(Transaction::class.java)
                .equalTo("isIncome", false)
                .sum("amount")
        return amount.toFloat()
    }

    fun getMonthlyIncome(date: Date): Float {
        val amount = Realm.getDefaultInstance().where(Transaction::class.java)
                .greaterThanOrEqualTo("date", date.monthStart())
                .lessThan("date", date.monthEnd())
                .equalTo("isIncome", true)
                .sum("amount")
        return amount.toFloat()
    }

    fun getMonthlyExpense(date: Date): Float {
        val amount = Realm.getDefaultInstance().where(Transaction::class.java)
                .greaterThanOrEqualTo("date", date.monthStart())
                .lessThan("date", date.monthEnd())
                .equalTo("isIncome", false)
                .sum("amount")
        return amount.toFloat()
    }

    fun getWeeklyIncome(date: Date): Float {
        val amount = Realm.getDefaultInstance().where(Transaction::class.java)
                .greaterThanOrEqualTo("date", date.weekStart())
                .lessThan("date", date.weekEnd())
                .equalTo("isIncome", true)
                .sum("amount")
        return amount.toFloat()
    }

    fun getWeeklyExpense(date: Date): Float {
        val amount = Realm.getDefaultInstance().where(Transaction::class.java)
                .greaterThanOrEqualTo("date", date.weekStart())
                .lessThan("date", date.weekEnd())
                .equalTo("isIncome", false)
                .sum("amount")
        return amount.toFloat()
    }

    fun getTodayIncome(date: Date): Float {
        val amount = Realm.getDefaultInstance().where(Transaction::class.java)
                .greaterThanOrEqualTo("date", date.todayStart())
                .lessThan("date", date.todayEnd())
                .equalTo("isIncome", true)
                .sum("amount")
        return amount.toFloat()
    }

    fun getTodayExpense(date: Date): Float {
        val amount = Realm.getDefaultInstance().where(Transaction::class.java)
                .greaterThanOrEqualTo("date", date.todayStart())
                .lessThan("date", date.todayEnd())
                .equalTo("isIncome", false)
                .sum("amount")
        return amount.toFloat()
    }
}