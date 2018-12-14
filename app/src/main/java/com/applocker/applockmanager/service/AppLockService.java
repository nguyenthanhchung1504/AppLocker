package com.applocker.applockmanager.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.applocker.applockmanager.activities.RequestPasswordActivity;
import com.applocker.applockmanager.databases.Database;
import com.applocker.applockmanager.models.Been;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class AppLockService extends Service {

    List<Been> list = new ArrayList<Been>();
    Been been = new Been();
    Database database;
    int appflag=0;
    int status;
    String appname;
    public AppLockService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        try {
            final long period = 300;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    //Log.d("Appname",getForegroundApp());

                    database = new Database(getApplicationContext());

                    list = database.AppCheck();

                    List<String> list1 = new ArrayList<String>();

                    for(Been been:list)
                    {
                        list1.add(been.getAppName());
                    }



                    SharedPreferences prefs1 = getSharedPreferences("Start", MODE_PRIVATE);
                    appflag = prefs1.getInt("appflag",0);
                    status = prefs1.getInt("status",0);

                    if(appflag==1) {
                        Log.d("flag", "1");

//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                try {
//                                    Thread.sleep(100);
//                                } catch (InterruptedException ex) {
//                                    // Handle ...
//                                }
//                            }
//                        }).start();

                        if (getForegroundApp() != "com.example.user.applocker") {
                            Log.d("myapp", "myapp");

                            String innermost = getForegroundApp();
                            Log.d("innermost before", innermost);
                            //Log.d("appname before", appname);

                            if (innermost.equals(appname)) {

                                String innermost1 = getForegroundApp();

                                Log.d("innermost after", innermost1);
                                Log.d("appname after", appname);

                                if (getForegroundApp() != "com.example.user.applocker") {
                                    Log.d("myapp", "myapp");

                                    SharedPreferences.Editor editor = getSharedPreferences("Start", MODE_PRIVATE).edit();
//                        editor.putInt("appflag",1);
                                    editor.putInt("status", 1);
                                    editor.putInt("appflag", 1);


                                    editor.apply();

                                }
                            }
                            if (innermost.equals(appname)==false){

                                Log.d("if not", getForegroundApp());

                                SharedPreferences.Editor editor = getSharedPreferences("Start", MODE_PRIVATE).edit();
                                // editor.putInt("appflag",0);
                                editor.putInt("status", 0);
                                editor.putInt("appflag", 0);

                                editor.apply();
                            }

                        }
                    }


                    if (list1.contains(getForegroundApp())) {

                        appname=getForegroundApp();

                        if(appflag==0&&status==0) {

                            Intent i = new Intent(getApplicationContext(), RequestPasswordActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(i);
                        }

                    }




                }
            }, 0, period);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        return super.onStartCommand(intent, flags, startId);
        //return START_NOT_STICKY;
    }


    public String getForegroundApp() {
        String currentApp = "NULL";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) this.getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            UsageEvents usageEvents = usm.queryEvents(time - 1000 * 30, System.currentTimeMillis() + (10 * 1000));
            UsageEvents.Event event = new UsageEvents.Event();
            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(event);
            }
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);

            if (appList != null && appList.size() > 0 )
            {
                TreeMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager) this.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }

        return currentApp;
    }

}
