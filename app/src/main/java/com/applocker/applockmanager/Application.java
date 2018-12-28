package com.applocker.applockmanager;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.zer.android.newsdk.ZAndroidSDK;

public class Application extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ZAndroidSDK.initApplication(this, this.getApplicationContext().getPackageName());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}