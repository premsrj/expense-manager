package com.premsuraj.expensemanager.startup

import com.premsuraj.expensemanager.Constants.REALM.AUTH_URL
import com.premsuraj.expensemanager.Constants.REALM.REALM_URL
import com.premsuraj.expensemanager.data.Category
import io.realm.*

class RealmStartup : StartupItem() {

    override fun startDataLoad(callback: StartupItemCallback?) {

        val userName = "expenses@gmail.com"
        val password = "Mf&9E2nTwPVZ5^BA"

        SyncUser.loginAsync(SyncCredentials.usernamePassword(userName, password, false), AUTH_URL, object : SyncUser.Callback<SyncUser?> {
            override fun onSuccess(result: SyncUser?) {

                val defaultConfig = SyncConfiguration.Builder(result!!, REALM_URL).build()
                Realm.setDefaultConfiguration(defaultConfig)
                setupDefaultData()
                callback?.onItemLoaded(this@RealmStartup)
            }

            override fun onError(error: ObjectServerError?) {
                callback?.onItemLoadFailed(this@RealmStartup)
            }
        })
    }

    private fun setupDefaultData() {
        val instance = Realm.getDefaultInstance()
        if (instance.where(Category::class.java).count() == 0L) {
            instance.beginTransaction()
            instance.insertOrUpdate(getDefaultCategoryList())
            instance.commitTransaction()
        }
    }

    private fun getDefaultCategoryList(): List<Category> {
        val list = ArrayList<Category>()

        list.add(Category()) //other
        val food = Category("", "Food", "")
        food.id = "" + food.hashCode()
        list.add(food)
        val snacks = Category("", "Snacks", food.id)
        snacks.id = "" + snacks.hashCode()
        list.add(snacks)
        val fruits = Category("", "Fruits", food.id)
        fruits.id = "" + fruits.hashCode()
        list.add(fruits)

        val family = Category("", "Family", "")
        family.id = "" + family.hashCode()
        list.add(family)
        val outing = Category("", "Outing", family.id)
        outing.id = "" + outing.hashCode()
        list.add(outing)

        val sana = Category("", "Sana", "")
        sana.id = "" + sana.hashCode()
        list.add(sana)
        val sanaFood = Category("", "Food", sana.id)
        sanaFood.id = "" + sanaFood.hashCode()
        list.add(sanaFood)

        val retirement = Category("", "Retirement", "")
        retirement.id = "" + retirement.hashCode()
        list.add(retirement)
        val mutualFund = Category("", "Mutual Funds", retirement.id)
        mutualFund.id = "" + mutualFund.hashCode()
        list.add(mutualFund)

        return list
    }

}