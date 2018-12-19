package com.applocker.applockmanager.service;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.applocker.applockmanager.R;
import com.applocker.applockmanager.activities.RequestPasswordActivity;
import com.applocker.applockmanager.databases.Database;
import com.applocker.applockmanager.models.Been;
import com.applocker.applockmanager.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class AppLockService extends Service {
    Timer timer;
    List<Been> list = new ArrayList<Been>();
    Been been = new Been();
    Database database;
    int appflag = 0;
    int status;
    String appname;
    public static final String CHANNEL_ID = "channel_id";
    public static final int NOTIFI_ID = 25;

    public AppLockService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();

    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        try {
            final long period = 2000;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    //Log.d("Appname",getForegroundApp());

                    database = new Database(getApplicationContext());

                    list = database.AppCheck();

                    List<String> list1 = new ArrayList<String>();

                    for (Been been : list) {
                        list1.add(been.getAppName());
                    }


                    SharedPreferences prefs1 = getSharedPreferences("Start", MODE_PRIVATE);
                    appflag = prefs1.getInt("appflag", 0);
                    status = prefs1.getInt("status", 0);

                    if (appflag == 1) {
                        if (getForegroundApp() != "com.applocker.applockmanager") {

                            String innermost = getForegroundApp();

                            if (innermost.equals(appname)) {

                                String innermost1 = getForegroundApp();
                                if (getForegroundApp() != "com.applocker.applockmanager") {
                                    SharedPreferences.Editor editor = getSharedPreferences("Start", MODE_PRIVATE).edit();
                                    editor.putInt("status", 1);
                                    editor.putInt("appflag", 1);
                                    editor.apply();


                                }
                            }
                            if (innermost.equals(appname) == false) {

                                SharedPreferences.Editor editor = getSharedPreferences("Start", MODE_PRIVATE).edit();
                                editor.putInt("status", 0);
                                editor.putInt("appflag", 0);
                                editor.apply();

                            }

                        }
                    }


                    if (list1.contains(getForegroundApp())) {

                        appname = getForegroundApp();

                        if (appflag == 0 && status == 0) {

                            Intent i = new Intent(getApplicationContext(), RequestPasswordActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i);
                        } else {
                            stopSelf();
                        }

                    }

                }
            }, 0, period);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }

        initNotification();
        createNotificationChannel();
        return super.onStartCommand(intent, flags, startId);
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

            if (appList != null && appList.size() > 0) {
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

    void initNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_icon)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.is_running))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        startForeground(NOTIFI_ID, mBuilder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel name";
            String description = "channeldescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
