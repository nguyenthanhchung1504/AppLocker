package com.applocker.applockmanager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.applocker.applockmanager.activities.CreatePinActivity;
import com.applocker.applockmanager.activities.EnterPasswordActivity;
import com.applocker.applockmanager.utils.Ads;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;
import com.zer.android.newsdk.ZAndroidSDK;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.layout_ads)
    RelativeLayout layoutAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        SharedPreferenceUtils utils = new SharedPreferenceUtils(this);
        boolean check = utils.getBoolanValue(Constant.PREFERENCES_SECOND, false);


        if (check) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashScreenActivity.this, EnterPasswordActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 3000);

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashScreenActivity.this, CreatePinActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 3000);
        }
        Ads.b(this, layoutAds, new Ads.OnAdsListener() {
            @Override
            public void onError() {
                layoutAds.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded() {
                layoutAds.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdOpened() {
                layoutAds.setVisibility(View.VISIBLE);
            }
        });

        Ads.f(this);
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.O) {
            ZAndroidSDK.init(this);
        }
    }



}
