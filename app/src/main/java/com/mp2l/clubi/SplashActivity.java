package com.mp2l.clubi;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import static java.lang.Thread.*;

public class SplashActivity extends Activity {

    // Splash screen timer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new FetchStats().execute();


    }

    private class FetchStats extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Thread.sleep(3000);
                // Do some stuff
            } catch (Exception e) {
                e.getLocalizedMessage();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    // Start your app main activity
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();

                }
            });
        }
    }
}
