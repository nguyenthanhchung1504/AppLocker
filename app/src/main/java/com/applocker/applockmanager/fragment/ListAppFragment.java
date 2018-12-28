package com.applocker.applockmanager.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.applocker.applockmanager.R;
import com.applocker.applockmanager.adapter.CustomList;
import com.applocker.applockmanager.models.Been;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAppFragment extends Fragment {
    private PackageManager packageManager = null;
    @BindView(R.id.applist)
    ListView applist;
    Unbinder unbinder;
    private SharedPreferenceUtils utils;
    private ArrayList<String> packagenameArray;
    private ArrayList<String> appnameArray;
    private ArrayList<Drawable> iconArray;
    private ArrayAdapter<String> arrayAdapter;
    private CustomList adapter;
    private ProgressDialog dialog;
    private String[] Stringarray;
    private Drawable[] Drawablearray;
    private View footerView;
    private boolean isLoading = false;
    private MyAsyncTask myAsyncTask;
    public ListAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_app, container, false);
        unbinder = ButterKnife.bind(this, view);

        packagenameArray = new ArrayList<String>();
        appnameArray = new ArrayList<String>();
        iconArray = new ArrayList<Drawable>();
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
//        new MyAsyncTask().execute();
        return view;
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getString(R.string.loading_data));
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PackageManager packageManager = getContext().getPackageManager();
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            List<ApplicationInfo> packs = packageManager.getInstalledApplications(0);
            Collections.sort(packs, new ApplicationInfo.DisplayNameComparator(packageManager));

            for (int i = 0; i < packs.size(); i++) {
                ApplicationInfo p = packs.get(i);
                if (packageManager.getLaunchIntentForPackage(p.packageName) != null) {
                    if (!p.packageName.equals("com.applocker.applockmanager")) {
                        if (!p.packageName.contains("launcher3")) {
                            if (!p.packageName.contains("launcher")) {//com.google.android.googlequicksearchbox
                                if (!p.packageName.contains("trebuchet")) {
                                    if (null != packageManager.getLaunchIntentForPackage(p.packageName)) {
                                        packagenameArray.add(p.packageName);
                                        appnameArray.add(p.loadLabel(getContext().getPackageManager()).toString());
                                        iconArray.add(p.loadIcon(getContext().getPackageManager()));
                                    }
                                }
                            }
                        }
                    }
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Stringarray = appnameArray.toArray(new String[appnameArray.size()]);
            String[] Stringarray1 = packagenameArray.toArray(new String[packagenameArray.size()]);
            Drawablearray = iconArray.toArray(new Drawable[iconArray.size()]);
            adapter = new CustomList(getContext(), Stringarray, Drawablearray, packagenameArray);
            applist.setAdapter(adapter);
            applist.setScrollingCacheEnabled(false);
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }

    }

    @Override
    public void onStop() {
        if(dialog != null ){
            dialog.cancel();
            myAsyncTask.cancel(true);
            getActivity().finishAffinity();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(dialog != null ){
            dialog.cancel();
        }
    }

}
