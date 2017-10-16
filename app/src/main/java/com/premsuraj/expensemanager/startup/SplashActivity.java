package com.premsuraj.expensemanager.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.premsuraj.expensemanager.R;
import com.premsuraj.expensemanager.base.BaseActivity;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new StartupManager().start(new StartupManagerCallback() {
            @Override
            public void onFinishedProcessing(ArrayList<StartupItem> passedItems, ArrayList<StartupItem> failedItems) {
                Intent intent = new Intent(SplashActivity.this, BaseActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
