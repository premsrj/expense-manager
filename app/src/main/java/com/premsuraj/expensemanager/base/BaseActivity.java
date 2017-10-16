package com.premsuraj.expensemanager.base;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.premsuraj.expensemanager.R;
import com.premsuraj.expensemanager.login.GoogleLoginManager;
import com.premsuraj.expensemanager.navigation.NavigationContainerListener;
import com.premsuraj.expensemanager.navigation.NavigationManager;
import com.premsuraj.expensemanager.utils.ObjectSerializer;

import java.io.File;

public class BaseActivity extends AppCompatActivity implements NavigationContainerListener, GoogleLoginManager.LoginListener {

    public NavigationManager navigationManager;
    GoogleLoginManager loginManager;

    protected void initBaseViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        loginManager = new GoogleLoginManager(this);
        navigationManager = new NavigationManager(this, this);
        navigationManager.initNavigationView((NavigationView) findViewById(R.id.nav_view));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void closeDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void login() {
        loginManager.login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginManager.removeAuthListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginManager.addAuthListener();
        checkLogin();
    }


    private void checkLogin() {
        GoogleLoginManager.UserDetails details = loginManager.getUserDetails();
        if (details != null) {
            navigationManager.userLoggedIn(this, details);
        } else {
            navigationManager.userLoggedOut();
        }
    }

    @Override
    public void loginSucceeded(GoogleLoginManager.UserDetails userDetails) {
        navigationManager.userLoggedIn(this, userDetails);
        String fileName = new File(this.getFilesDir(), "userdetails.dat").getAbsolutePath();
        new ObjectSerializer().putObject(fileName, userDetails);
    }
}
