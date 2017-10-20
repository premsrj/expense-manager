package com.premsuraj.expensemanager.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.premsuraj.expensemanager.R
import com.premsuraj.expensemanager.base.BaseActivity

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

//        realm.executeTransaction { realm ->
//            val other = Category("", "Other", "")
//            other.id = "" + other.hashCode()
//
//            val sana = Category("", "Sana", "")
//            sana.id = "" + sana.hashCode()
//            val sanaFood = Category("", "Food", sana.id)
//            sanaFood.id = "" + sanaFood.hashCode()
//            val sanaSchool = Category("", "School", sana.id)
//            sanaSchool.id = "" + sanaSchool.hashCode()
//
//            val vedhika = Category("", "Vedhika", "")
//            vedhika.id = "" + vedhika.hashCode()
//            val government = Category("", "Government", vedhika.id)
//            government.id = "" + government.hashCode()
//            val loan = Category("", "Loan", vedhika.id)
//            loan.id = "" + loan.hashCode()
//            val electrical = Category("", "Electrical", vedhika.id)
//            electrical.id = "" + electrical.hashCode()
//
//            realm.insertOrUpdate(listOf(other, sana, sanaFood, sanaSchool, vedhika, government, loan, electrical))
//        }

        super.initBaseViews()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }
}