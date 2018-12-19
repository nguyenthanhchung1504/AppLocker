package com.applocker.applockmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;


import com.applocker.applockmanager.R;
import com.applocker.applockmanager.adapter.ThemeAdapter;
import com.applocker.applockmanager.models.Theme;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeThemeActivity extends AppCompatActivity {

    @BindView(R.id.lst_theme)
    RecyclerView lstTheme;
    @BindView(R.id.layout_change_theme)
    ConstraintLayout layoutChangeTheme;
    private ThemeAdapter adapter;
    private SharedPreferenceUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_theme);
        ButterKnife.bind(this);
        utils = new SharedPreferenceUtils(this);
        int pos = utils.getIntValue(Constant.CHANGE_THEME, 0);
        if (pos == 0) {
            layoutChangeTheme.setBackgroundResource(R.drawable.bg_1);
        }
        if (pos == 1) {
            layoutChangeTheme.setBackgroundResource(R.drawable.bg_2);
        }
        if (pos == 2) {
            layoutChangeTheme.setBackgroundResource(R.drawable.bg_3);
        }
        if (pos == 3) {
            layoutChangeTheme.setBackgroundResource(R.drawable.bg_4);
        }
        if (pos == 4) {
            layoutChangeTheme.setBackgroundResource(R.drawable.bg_5);
        }
        if (pos == 5) {
            layoutChangeTheme.setBackgroundResource(R.drawable.bg_6);
        }

        lstTheme.setLayoutManager(new GridLayoutManager(this, 2));
        lstTheme.setHasFixedSize(true);
        ArrayList<Theme> themeArrayList = new ArrayList<>();
        themeArrayList.add(new Theme(1, R.drawable.ss_pass));
        themeArrayList.add(new Theme(2, R.drawable.ss_pass_2));
        themeArrayList.add(new Theme(3, R.drawable.ss_pass_3));
        themeArrayList.add(new Theme(4, R.drawable.ss_pass_4));
        themeArrayList.add(new Theme(5, R.drawable.ss_pass_5));
        themeArrayList.add(new Theme(6, R.drawable.ss_pass_6));
        adapter = new ThemeAdapter(this, themeArrayList);
        lstTheme.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        startActivity(new Intent(this,SettingActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            startActivity(new Intent(this,SettingActivity.class));
            finish();
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finishAffinity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finishAffinity();
    }
}
