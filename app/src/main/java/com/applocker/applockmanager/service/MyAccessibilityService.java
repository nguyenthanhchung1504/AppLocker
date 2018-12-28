package com.applocker.applockmanager.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import com.applocker.applockmanager.activities.RequestPasswordActivity;
import com.applocker.applockmanager.databases.Database;
import com.applocker.applockmanager.models.Been;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class MyAccessibilityService extends AccessibilityService {
    AccessibilityEvent event;
    AccessibilityServiceInfo config;
    private String prevActivity = "";
    List<Been> list = new ArrayList<Been>();
    Been been = new Been();
    Database database;
    int appflag = 0;
    int status;
    String appname;
    public static String sActivityName;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        prevActivity = getPackageName();
        config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        setServiceInfo(config);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        setServiceInfo(config);
        super.onStartCommand(intent, flags, startId);
        try {
            final long period = 2000;
            new Timer().schedule(new TimerTask() {
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
                        if (!getForegroundApp().equals("com.applocker.applockmanager")) {

                            String innermost = getForegroundApp();

                            if (innermost.equals(appname)) {

                                String innermost1 = getForegroundApp();
                                if (!getForegroundApp().equals("com.applocker.applockmanager")) {
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
                        AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
                        AccessibilityEvent event = AccessibilityEvent.obtain();
                        event.setEventType(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED);
                        manager.sendAccessibilityEvent(event);
                        appname = getForegroundApp();
                        if (appflag == 0 && status == 0) {
                            if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                                Intent i = new Intent(getApplicationContext(), RequestPasswordActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT |
                                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);

                            }
                        }
                    }
            }

        },0, period);
    } catch(
    Exception e)

    {
        Thread.currentThread().interrupt();
    }
//        }
        return START_STICKY;

}

    private static boolean isForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : tasks) {
            if (ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND == appProcess.importance
                    && appProcess.importanceReasonCode == ActivityManager.RunningAppProcessInfo.REASON_UNKNOWN
                    && !packageName.equals(appProcess.processName)) {
                if (context.getPackageName() != null) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            sActivityName = event.getClassName().toString();
        }
    }

    private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo((componentName), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public void onInterrupt() {

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
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_WEEKLY, time - 1000 * 1000, time);

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
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            ComponentName componentName = tasks.get(0).topActivity;
            currentApp = componentName.getPackageName();
        }
        return currentApp;
    }
}
