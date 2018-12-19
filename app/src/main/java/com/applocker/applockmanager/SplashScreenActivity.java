package com.applocker.applockmanager;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.applocker.applockmanager.activities.CreatePinActivity;
import com.applocker.applockmanager.activities.EnterPasswordActivity;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferenceUtils utils = new SharedPreferenceUtils(this);
        boolean check = utils.getBoolanValue(Constant.PREFERENCES_SECOND,false);


        if (check){
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashScreenActivity.this,EnterPasswordActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 3000);

        }
        else {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashScreenActivity.this,CreatePinActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 3000);
        }

    }

}
