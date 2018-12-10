package com.applocker.applockmanager.activities;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.applocker.R;
import com.applocker.applockmanager.adapter.ListAppAdapter;
import com.applocker.applockmanager.databases.DatabaseManager;
import com.applocker.applockmanager.models.App;
import com.applocker.applockmanager.service.LockAppService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListApplicationActivity extends AppCompatActivity {
    ListAppAdapter adapter;
    List<App> mAppsInDevice, mAppsFromDB, mAppsAreLocking, mTempApps;
    @BindView(R.id.lst_application)
    RecyclerView lstApplication;
    ProgressDialog dialog;
    public static DatabaseManager mDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_application);
        ButterKnife.bind(this);
        if (!checkUsageAccess()) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }

        mDBManager = new DatabaseManager(this);
        new MyAsynTask().execute();


        Intent intent = new Intent(this, LockAppService.class);
        startService(intent);


    }

    private void getAllAppFromDevide() {
        PackageManager pm = this.getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);

        for (ApplicationInfo app : apps) {

            Drawable icon = app.loadIcon(pm);
            String name = app.loadLabel(pm).toString();
            String packageName = app.packageName;

            //type 1: app is installed
            //type 0: it is a system app
            //state 0: lock off
            if ((app.flags & (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP | ApplicationInfo.FLAG_SYSTEM)) > 0) {
                // It is a system app
                mAppsInDevice.add(new App(icon, name, packageName, 0, 0));

            } else {
                // It is installed by the user
                mAppsInDevice.add(new App(icon, name, packageName, 1, 0));
            }
        }

        mTempApps = new ArrayList<>(mAppsInDevice);
    }

    public class MyAsynTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mAppsInDevice = new ArrayList<>();

            //show dialog
            dialog = new ProgressDialog(ListApplicationActivity.this);
            dialog.setMessage("Loading data...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getAllAppFromDevide();
            mAppsFromDB = mDBManager.getAllApp();
            mAppsAreLocking = mDBManager.getAppsAreLocking();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            syncDataFromDBToUI();

            addNewAppsInstallIntoDb();
            setAdapter();

            //hide dialog
            dialog.dismiss();
        }
    }

    void syncDataFromDBToUI() {
        int i = 0;
        for (App app : mAppsAreLocking) {
            for (; i < mAppsInDevice.size(); i++) {
                if (!app.getPackageName().equals(mAppsInDevice.get(i).getPackageName())) {
                    continue;
                } else {
                    mAppsInDevice.get(i).setState(1);
                    break;
                }
            }
            if (i == mAppsInDevice.size())
                mDBManager.deleteApp(app);

            i = 0;
        }
    }

    void setAdapter() {
        adapter = new ListAppAdapter(this, mAppsInDevice);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lstApplication.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        lstApplication.addItemDecoration(itemDecoration);
        lstApplication.setHasFixedSize(true);
        lstApplication.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void addNewAppsInstallIntoDb() {
        mTempApps.removeAll(mAppsFromDB);
        for (App app : mTempApps) {
            mDBManager.addApp(app);
            mAppsFromDB.add(app);
        }
    }


    boolean checkUsageAccess() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
