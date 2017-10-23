package com.premsuraj.expensemanager.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.premsuraj.expensemanager.utils.toFormattedString
import com.premsuraj.expensemanager.R
import org.jetbrains.anko.find
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class HomeActivityFragment : Fragment() {
    lateinit var totalBalance: TextView
    lateinit var monthIncome: TextView
    lateinit var monthExpense: TextView
    lateinit var monthBalance: TextView
    lateinit var weekIncome: TextView
    lateinit var weekExpense: TextView
    lateinit var weekBalance: TextView
    lateinit var todayIncome: TextView
    lateinit var todayExpense: TextView
    lateinit var todayBalance: TextView
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val inflatedView = inflater.inflate(R.layout.fragment_home, container, false)

        totalBalance = inflatedView.find(R.id.totalBalance)
        monthIncome = inflatedView.find(R.id.monthIncome)
        monthExpense = inflatedView.find(R.id.monthExpense)
        monthBalance = inflatedView.find(R.id.monthBalance)
        weekIncome = inflatedView.find(R.id.weekIncome)
        weekExpense = inflatedView.find(R.id.weekExpense)
        weekBalance = inflatedView.find(R.id.weekBalance)
        todayIncome = inflatedView.find(R.id.todayIncome)
        todayExpense = inflatedView.find(R.id.todayExpense)
        todayBalance = inflatedView.find(R.id.todayBalance)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        return inflatedView
    }

    override fun onResume() {
        super.onResume()

        setTotalValues()
        setMonthlyValues()
        setWeeklyValues()
        setTodayValues()
    }

    private fun setTotalValues() {
        val income = viewModel.getTotalIncome()
        val expense = viewModel.getTotalExpense()

        totalBalance.text = (income - expense).toFormattedString()
    }

    private fun setMonthlyValues() {
        val income = viewModel.getMonthlyIncome(Date())
        val expense = viewModel.getMonthlyExpense(Date())
        monthIncome.text = income.toFormattedString()
        monthExpense.text = expense.toFormattedString()
        monthBalance.text = (income - expense).toFormattedString()

        if (income - expense < 0)
            monthBalance.setTextColor(resources.getColor(R.color.expenseColor))
        else
            monthBalance.setTextColor(resources.getColor(R.color.incomeColor))
    }

    private fun setWeeklyValues() {
        val income = viewModel.getWeeklyIncome(Date())
        val expense = viewModel.getWeeklyExpense(Date())
        weekIncome.text = income.toFormattedString()
        weekExpense.text = expense.toFormattedString()
        weekBalance.text = (income - expense).toFormattedString()

        if (income - expense < 0)
            weekBalance.setTextColor(resources.getColor(R.color.expenseColor))
        else
            weekBalance.setTextColor(resources.getColor(R.color.incomeColor))
    }

    private fun setTodayValues() {
        val income = viewModel.getTodayIncome(Date())
        val expense = viewModel.getTodayExpense(Date())
        todayIncome.text = income.toFormattedString()
        todayExpense.text = expense.toFormattedString()
        todayBalance.text = (income - expense).toFormattedString()

        if (income - expense < 0)
            todayBalance.setTextColor(resources.getColor(R.color.expenseColor))
        else
            todayBalance.setTextColor(resources.getColor(R.color.incomeColor))
    }
}
