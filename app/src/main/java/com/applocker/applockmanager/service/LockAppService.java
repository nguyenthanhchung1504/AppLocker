package com.applocker.applockmanager.service;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.applocker.applockmanager.R;
import com.applocker.applockmanager.activities.ListApplicationActivity;
import com.applocker.applockmanager.activities.RequestPasswordActivity;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class LockAppService extends Service {
    public static final String CHANNEL_ID = "channel_id";
    public static final int NOTIFI_ID = 25;
    String currentApp;
    boolean serviceStatus;
    public LockAppService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferenceUtils utils = new SharedPreferenceUtils(this);
        serviceStatus = utils.getBoolanValue(Constant.STATUS,true);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    retriveNewApp();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();

        createNotificationChannel();
        initNotification();

        return START_NOT_STICKY;
    }

    void initNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_icon)
                .setContentTitle("")
                .setContentText("")
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    private void retriveNewApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            currentApp = null;
            UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (applist != null && applist.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
                for (UsageStats usageStats : applist) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }

            Log.d("AAA", "Current App in foreground is: " + currentApp);
            if (currentApp != null)
                checkMyApp(currentApp);
        } else {
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            String mm = (manager.getRunningTasks(1).get(0)).topActivity.getPackageName();
            List<ActivityManager.RunningAppProcessInfo> task = manager.getRunningAppProcesses();
            currentApp = task.get(0).processName;
            Log.d("AAA", "Current App in foreground is: " + mm);

            if (mm != null)
                checkMyApp(mm);
        }
    }

    void checkMyApp(String currentApp) {
        if (ListApplicationActivity.mDBManager.getAppByPackageName(currentApp).getState() == 1) {
            if (serviceStatus == true){
                Intent intent = new Intent(this, RequestPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        }
    }
}
