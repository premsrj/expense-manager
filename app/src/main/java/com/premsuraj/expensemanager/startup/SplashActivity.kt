package com.premsuraj.expensemanager.startup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.premsuraj.expensemanager.R
import com.premsuraj.expensemanager.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        StartupManager().addStartupItems(RealmStartup()).start { passedItems, failedItems ->
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}