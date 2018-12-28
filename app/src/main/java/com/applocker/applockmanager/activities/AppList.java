package com.applocker.applockmanager.activities;

import android.app.AppOpsManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.applocker.applockmanager.R;
import com.applocker.applockmanager.adapter.CustomList;
import com.applocker.applockmanager.fragment.ListAppFragment;
import com.applocker.applockmanager.service.AppLockService;
import com.applocker.applockmanager.utils.Ads;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;
import com.bumptech.glide.Glide;
import com.zer.android.newsdk.ZAndroidSDK;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppList extends AppCompatActivity {
    @BindView(R.id.layout_main)
    RelativeLayout layoutMain;
    @BindView(R.id.constraintLayout2)
    FrameLayout constraintLayout2;
    @BindView(R.id.layout_ads)
    RelativeLayout layoutAds;
    private ListView applist;
    private ImageView img_main, img_setting;
    private SharedPreferenceUtils utils;
    private boolean check_on_off;
    private ProgressDialog dialog;
    ArrayList<String> packagenameArray;
    ArrayList<String> appnameArray;
    ArrayList<Drawable> iconArray;
    ArrayAdapter<String> arrayAdapter;
    private CustomList adapter;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        ButterKnife.bind(this);

        utils = new SharedPreferenceUtils(this);
        applist = (ListView) findViewById(R.id.applist);
        img_main = findViewById(R.id.img_main);
        img_setting = findViewById(R.id.img_setting);

        check_on_off = utils.getBoolanValue(Constant.SWITCH_ON_OFF, true);
        if (!isAccessGranted()) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
        if (check_on_off == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent intent = new Intent(this, AppLockService.class);
                intent.setAction(AppLockService.CHANNEL_ID);
                startForegroundService(intent);
            } else {
                Intent intent = new Intent(this, AppLockService.class);
                intent.setAction(AppLockService.CHANNEL_ID);
                startService(intent);
            }
//
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent intent = new Intent(this, AppLockService.class);
                stopService(intent);
            } else {
                Intent intent = new Intent(this, AppLockService.class);
                stopService(intent);
            }

        }


        SharedPreferences.Editor editor = getSharedPreferences("Start", MODE_PRIVATE).edit();
        editor.putInt("flag", 1);
        editor.apply();


        int pos = utils.getIntValue(Constant.CHANGE_THEME, 0);
        if (pos == 0) {
            layoutMain.setBackgroundResource(R.drawable.bg_1);
        }
        if (pos == 1) {
            layoutMain.setBackgroundResource(R.drawable.bg_2);
        }
        if (pos == 2) {
            layoutMain.setBackgroundResource(R.drawable.bg_3);
        }
        if (pos == 3) {
            layoutMain.setBackgroundResource(R.drawable.bg_4);
        }
        if (pos == 4) {
            layoutMain.setBackgroundResource(R.drawable.bg_5);
        }
        if (pos == 5) {
            layoutMain.setBackgroundResource(R.drawable.bg_6);
        }


        Glide.with(this).load(R.drawable.ic_main).into(img_main);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.constraintLayout2, new ListAppFragment(), "aaa");
        transaction.commit();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ZAndroidSDK.REQUEST_READ_EX:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ZAndroidSDK.onPermissionGranted(this);
                }
                break;
            case ZAndroidSDK.REQUEST_WRITE_EX:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ZAndroidSDK.onPermissionGranted(this);
                }
                break;
        }
    }
    private boolean isAccessGranted() {
        try {
            PackageManager packageManager = this.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) this.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (isAccessGranted()) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(startMain);
            }

        }

        return false;
    }

    @OnClick(R.id.img_setting)
    public void onViewClicked() {
        startActivity(new Intent(this, SettingActivity.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isAccessGranted()) {
            finishAffinity();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isAccessGranted()) {
            finishAffinity();
        }
    }


}

