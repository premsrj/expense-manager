package com.premsuraj.expensemanager.navigation;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.premsuraj.expensemanager.R;
import com.premsuraj.expensemanager.login.GoogleLoginManager;

/**
 * Created by Premsuraj
 */

public class NavigationManager
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationContainerListener mListener;
    private Activity currentActivity;
    private ImageView userImage;
    private TextView userName;
    private View signInButton;

    public NavigationManager(Activity currentActivity, NavigationContainerListener listener) {
        this.currentActivity = currentActivity;
        this.mListener = listener;
    }

    public void initNavigationView(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(this);
        userImage = navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        userName = navigationView.getHeaderView(0).findViewById(R.id.user_name);
        signInButton = navigationView.getHeaderView(0).findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.login();
                }
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if (mListener != null)
            mListener.closeDrawer();
        return true;
    }

    public void userLoggedIn(Context context, GoogleLoginManager.UserDetails userDetails) {
        userImage.setVisibility(View.VISIBLE);
        userName.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);
        Glide.with(context).load(userDetails.imageUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userImage);

        userName.setText(userDetails.userName);
    }

    public void userLoggedOut() {
        userImage.setVisibility(View.GONE);
        userName.setVisibility(View.GONE);
        signInButton.setVisibility(View.VISIBLE);
    }
}
