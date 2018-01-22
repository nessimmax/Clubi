package com.mp2l.clubi;

/**
 * Created by AdminPC on 1/1/2018.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Typeface;
import android.widget.Toast;
import android.support.v7.app.ActionBarDrawerToggle;

import com.sirvar.robin.RobinActivity;
import com.sirvar.robin.Theme;

public class MainActivity extends RobinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLoginTitle("Sign in to clubi");
        setSignupTitle("Welcome to clubi");
        setForgotPasswordTitle("Forgot Password");
        setImage(getResources().getDrawable(R.drawable.logo));
        setFont(Typeface.createFromAsset(getAssets(), "Montserrat-Medium.ttf"));
        setTheme(Theme.LIGHT);
        enableSocialLogin();
        showLoginFirst();
    }

    @Override
    protected void onLogin(String email, String password) {
        Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            public void run() {
                // Start your app main activity
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);

                // close this activity
                finish();

            }
        });
    }

    @Override
    protected void onSignup(String name, String email, String password) {
        Toast.makeText(getApplicationContext(), "Signup", Toast.LENGTH_SHORT).show();
        // Make API call
    }

    @Override
    protected void onForgotPassword(String email) {
        Toast.makeText(getApplicationContext(), "Forgot Password", Toast.LENGTH_SHORT).show();
        // Make API call
        // After sent password email callback
        startLoginFragment();
    }

    @Override
    protected void onGoogleLogin() {
        Toast.makeText(getApplicationContext(), "Google", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onFacebookLogin() {
        Toast.makeText(getApplicationContext(), "Facebook", Toast.LENGTH_SHORT).show();
    }
}
