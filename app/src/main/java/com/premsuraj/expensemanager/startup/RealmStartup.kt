package com.premsuraj.expensemanager.startup

import com.premsuraj.expensemanager.Constants.REALM.AUTH_URL
import com.premsuraj.expensemanager.Constants.REALM.REALM_URL
import io.realm.*

class RealmStartup : StartupItem() {

    override fun startDataLoad(callback: StartupItemCallback?) {

        val userName = "expenses@gmail.com"
        val password = "Mf&9E2nTwPVZ5^BA"

        SyncUser.loginAsync(SyncCredentials.usernamePassword(userName, password, false), AUTH_URL, object : SyncUser.Callback<SyncUser?> {
            override fun onSuccess(result: SyncUser?) {

                val defaultConfig = SyncConfiguration.Builder(result!!, REALM_URL).build()
                Realm.setDefaultConfiguration(defaultConfig)
                callback?.onItemLoaded(this@RealmStartup)
            }

            override fun onError(error: ObjectServerError?) {
                callback?.onItemLoadFailed(this@RealmStartup)
            }
        })
    }

}